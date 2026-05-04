package com.abcaa.sistema_atividades.service;

import com.abcaa.sistema_atividades.domain.entity.Activity;
import com.abcaa.sistema_atividades.domain.entity.Department;
import com.abcaa.sistema_atividades.domain.entity.Volunteer;
import com.abcaa.sistema_atividades.domain.enums.ActivityStatus;
import com.abcaa.sistema_atividades.domain.enums.UserType;
import com.abcaa.sistema_atividades.dto.ActivityDTO;
import com.abcaa.sistema_atividades.dto.ActivityReportDTO;
import com.abcaa.sistema_atividades.dto.PagedResponseDTO;
import com.abcaa.sistema_atividades.dto.VolunteerRankingDTO;
import com.abcaa.sistema_atividades.mapper.ActivityMapper;
import com.abcaa.sistema_atividades.repository.ActivityRepository;
import com.abcaa.sistema_atividades.repository.VolunteerRepository;
import com.abcaa.sistema_atividades.validation.ValidationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ActivityService - Testes de Atividades")
class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private VolunteerRepository volunteerRepository;
    @Mock
    private ActivityMapper activityMapper;
    @Mock
    private ValidationService validationService;

    @InjectMocks
    private ActivityService activityService;
    private ActivityDTO dto;
    private Volunteer volunteer;
    private Department department;
    private Activity activity;

    @BeforeEach
    void setUp() {

        department = new Department(1L, "Administrativo");

        volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setName("João Silva");
        volunteer.setEmail("joao@test.com");
        volunteer.setPassword("encodedPassword");
        volunteer.setDepartment(department);
        volunteer.setUserType(UserType.VOLUNTEER);

        dto = new ActivityDTO();
        dto.setId(1L);
        dto.setVolunteerId(volunteer.getId());
        dto.setVolunteerName(volunteer.getUsername());
        dto.setDepartmentId(department.getId());
        dto.setDepartmentName(department.getName());
        dto.setTitle("Aplicação de testes");
        dto.setDescription("Implementação de testes");
        dto.setActivityStatus(ActivityStatus.PENDING);
        dto.setDurationMinutes(90);

        activity = new Activity();
        activity.setId(10L);
        activity.setTitle("Testes");
    }


    @Test
    @DisplayName("Deve criar atividade quando dados são válidos")
    void shouldCreateNewActivitySuccessfully() {

        when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));
        when(activityMapper.toEntity(dto, volunteer)).thenReturn(activity);
        when(activityRepository.save(activity)).thenReturn(activity);
        when(activityMapper.toDTO(activity)).thenReturn(dto);

        ActivityDTO result = activityService.create(dto);

        assertNotNull(result);
        assertEquals(ActivityStatus.PENDING, activity.getActivityStatus());

        verify(validationService).validateActivity(dto);
        verify(volunteerRepository).findById(1L);
        verify(activityMapper).toEntity(dto, volunteer);
        verify(activityRepository).save(activity);
        verify(activityMapper).toDTO(activity);
    }


    @Test
    @DisplayName("deve retornar DTO quando atividade é encontrada por ID")
    void shouldFindByIdSuccessfully() {

        when(activityRepository.findById(10L)).thenReturn(Optional.of(activity));
        when(activityMapper.toDTO(activity)).thenReturn(dto);

        ActivityDTO result = activityService.findById(10L);

        assertNotNull(result);
        assertEquals(dto.getTitle(), result.getTitle());
        assertEquals(dto.getId(), result.getId());
    }


    @Test
    @DisplayName("deve lançar exceção quando atividade não é encontrada")
    void shouldThrowExceptionWhenActivityNotFound() {

        when(activityRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> activityService.findById(99L));
    }


    @Test
    @DisplayName("Deve retornar página de atividades do voluntário ordenadas por data desc")
    void shouldfindByActivityVolunteerIdSuccessfully() {

        int page = 0;
        int size = 10;

        Activity activity1 = new Activity();
        activity1.setId(1L);
        Activity activity2 = new Activity();
        activity2.setId(2L);

        ActivityDTO dto1 = new ActivityDTO();
        dto1.setId(1L);
        ActivityDTO dto2 = new ActivityDTO();
        dto2.setId(2L);

        List<Activity> activities = List.of(activity1, activity2);
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Activity> activityPage = new PageImpl<>(activities, pageable, 2);

        when(activityRepository.findByVolunteerId(eq(volunteer.getId()), any(Pageable.class)))
                .thenReturn(activityPage);
        when(activityMapper.toDTO(activity1)).thenReturn(dto1);
        when(activityMapper.toDTO(activity2)).thenReturn(dto2);

        PagedResponseDTO<ActivityDTO> resultado = activityService.findByVolunteerId(volunteer.getId(), page, size);

        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        assertEquals(dto1, resultado.getContent().get(0));
        assertEquals(dto2, resultado.getContent().get(1));
        assertEquals(0, resultado.getCurrentPage());
        assertEquals(10, resultado.getPageSize());
        assertEquals(2L, resultado.getTotalElements());
        assertEquals(1, resultado.getTotalPages());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(activityRepository).findByVolunteerId(eq(volunteer.getId()), pageableCaptor.capture());

        Pageable capturado = pageableCaptor.getValue();
        assertEquals(page, capturado.getPageNumber());
        assertEquals(size, capturado.getPageSize());
        assertEquals(Sort.by("date").descending(), capturado.getSort());
    }


    @Test
    @DisplayName("deve retornar página de atividades filtradas por status")
    void findActivitiesByStatus() {

        int page = 0;
        int size = 10;

        Activity activity1 = new Activity();
        activity1.setId(1L);
        Activity activity2 = new Activity();
        activity2.setId(2L);

        ActivityDTO dto1 = new ActivityDTO();
        dto1.setId(1L);
        ActivityDTO dto2 = new ActivityDTO();
        dto2.setId(2L);

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Activity> activityPage = new PageImpl<>(List.of(activity1, activity2), pageable, 2);

        when(activityRepository.findByActivityStatus(eq(ActivityStatus.PENDING), any(Pageable.class)))
                .thenReturn(activityPage);
        when(activityMapper.toDTO(activity1)).thenReturn(dto1);
        when(activityMapper.toDTO(activity2)).thenReturn(dto2);

        PagedResponseDTO<ActivityDTO> resultado = activityService.findActivitiesByStatus(ActivityStatus.PENDING, page, size);

        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        assertEquals(dto1, resultado.getContent().get(0));
        assertEquals(dto2, resultado.getContent().get(1));
        assertEquals(0, resultado.getCurrentPage());
        assertEquals(10, resultado.getPageSize());
        assertEquals(2L, resultado.getTotalElements());
    }


    @Test
    @DisplayName("deve atualizar atividade quando dados são válidos")
    void activityUpdate() {

        when(activityRepository.findById(10L)).thenReturn(Optional.of(activity));
        when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));
        when(activityRepository.save(activity)).thenReturn(activity);
        when(activityMapper.toDTO(activity)).thenReturn(dto);

        ActivityDTO result = activityService.activityUpdate(10L, dto);

        assertNotNull(result);
        verify(validationService).validateActivityUpdate(activity);
        verify(validationService).validateActivity(dto);
        verify(activityRepository).save(activity);
    }

    @Test
    @DisplayName("deve lançar exceção ao atualizar atividade não encontrada")
    void shouldThrowWhenActivityNotFoundOnUpdate() {

        when(activityRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> activityService.activityUpdate(99L, dto));
    }


    @Test
    @DisplayName("deve atualizar status da atividade com sucesso")
    void statusUpdate() {

        when(activityRepository.findById(10L)).thenReturn(Optional.of(activity));
        when(activityRepository.save(activity)).thenReturn(activity);
        when(activityMapper.toDTO(activity)).thenReturn(dto);

        ActivityDTO result = activityService.statusUpdate(10L, ActivityStatus.APPROVED);

        assertNotNull(result);
        assertEquals(ActivityStatus.APPROVED, activity.getActivityStatus());
        verify(activityRepository).save(activity);
    }

    @Test
    @DisplayName("deve lançar exceção ao atualizar status de atividade não encontrada")
    void shouldThrowWhenActivityNotFoundOnStatusUpdate() {

        when(activityRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> activityService.statusUpdate(99L, ActivityStatus.APPROVED));
    }


    @Test
    @DisplayName("deve retornar todas as atividades paginadas")
    void findAll() {

        int page = 0;
        int size = 10;

        Activity activity1 = new Activity();
        activity1.setId(1L);
        ActivityDTO dto1 = new ActivityDTO();
        dto1.setId(1L);

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Activity> activityPage = new PageImpl<>(List.of(activity1), pageable, 1);

        when(activityRepository.findAll(any(Pageable.class))).thenReturn(activityPage);
        when(activityMapper.toDTO(activity1)).thenReturn(dto1);

        PagedResponseDTO<ActivityDTO> resultado = activityService.findAll(page, size);

        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        assertEquals(dto1, resultado.getContent().get(0));
        assertEquals(1L, resultado.getTotalElements());
    }


    @Test
    @DisplayName("deve retornar relatório de atividades aprovadas do voluntário")
    void getReport() {

        Activity activity1 = new Activity();
        activity1.setId(1L);
        activity1.setDurationMinutes(60);

        Activity activity2 = new Activity();
        activity2.setId(2L);
        activity2.setDurationMinutes(90);

        ActivityDTO dto1 = new ActivityDTO();
        ActivityDTO dto2 = new ActivityDTO();

        when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));
        when(activityRepository.findByVolunteerIdAndActivityStatus(1L, ActivityStatus.APPROVED))
                .thenReturn(List.of(activity1, activity2));
        when(activityMapper.toDTO(activity1)).thenReturn(dto1);
        when(activityMapper.toDTO(activity2)).thenReturn(dto2);

        ActivityReportDTO result = activityService.getReport(1L, null, null);

        assertNotNull(result);
        assertEquals(1L, result.getVolunteerId());
        assertEquals("João Silva", result.getVolunteerName());
        assertEquals("Administrativo", result.getDepartment());
        assertEquals(150, result.getTotalMinutes());
        assertEquals(2, result.getTotalActivities());
    }


    @Test
    @DisplayName("deve retornar ranking de voluntários com posições corretas")
    void getRanking() {

        LocalDate start = LocalDate.of(2026, 4, 1);
        LocalDate end = LocalDate.of(2026, 4, 30);

        Object[] row1 = {1L, "João Silva", "Administrativo", 120, 2};
        Object[] row2 = {2L, "Maria Santos", "Financeiro", 60, 1};

        when(activityRepository.findRanking(start, end)).thenReturn(List.of(row1, row2));

        List<VolunteerRankingDTO> result = activityService.getRanking(start, end);

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1, result.get(0).getRank());
        assertEquals(1L, result.get(0).getVolunteerId());
        assertEquals("João Silva", result.get(0).getVolunteerName());
        assertEquals(120, result.get(0).getTotalMinutes());

        assertEquals(2, result.get(1).getRank());
        assertEquals(2L, result.get(1).getVolunteerId());
    }
}
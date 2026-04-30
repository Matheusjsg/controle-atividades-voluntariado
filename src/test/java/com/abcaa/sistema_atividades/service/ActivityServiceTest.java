package com.abcaa.sistema_atividades.service;

import com.abcaa.sistema_atividades.domain.entity.Activity;
import com.abcaa.sistema_atividades.domain.entity.Department;
import com.abcaa.sistema_atividades.domain.entity.Volunteer;
import com.abcaa.sistema_atividades.domain.enums.ActivityStatus;
import com.abcaa.sistema_atividades.domain.enums.UserType;
import com.abcaa.sistema_atividades.dto.ActivityDTO;
import com.abcaa.sistema_atividades.dto.PagedResponseDTO;
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
    private Pageable pageable;

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
    @DisplayName("Deve retornar página de atividades do voluntário_Id ordenadas por data desc")
    void shouldfindByActivityVolunteerIdSuccessfully() {

        // Arrange
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
        pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Activity> activityPage = new PageImpl<>(activities, pageable, 2);

        when(activityRepository.findByVolunteerId(eq(volunteer.getId()), any(Pageable.class)))
                .thenReturn(activityPage);
        when(activityMapper.toDTO(activity1)).thenReturn(dto1);
        when(activityMapper.toDTO(activity2)).thenReturn(dto2);

        // Act
        PagedResponseDTO<ActivityDTO> resultado = activityService.findByVolunteerId(volunteer.getId(), page, size);

        // Assert - conteúdo
        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        assertEquals(dto1, resultado.getContent().get(0));
        assertEquals(dto2, resultado.getContent().get(1));

        // Assert - metadados de paginação
        assertEquals(0, resultado.getCurrentPage());
        assertEquals(10, resultado.getPageSize());
        assertEquals(2L, resultado.getTotalElements());
        assertEquals(1, resultado.getTotalPages());

        // Verifica se o Pageable foi montado corretamente (page, size e ordenação)
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(activityRepository).findByVolunteerId(eq(volunteer.getId()), pageableCaptor.capture());

        Pageable capturado = pageableCaptor.getValue();
        assertEquals(page, capturado.getPageNumber());
        assertEquals(size, capturado.getPageSize());
        assertEquals(Sort.by("date").descending(), capturado.getSort());

    }


    @Test
    void findActivitiesByStatus() {






    }

    @Test
    void activityUpdate() {
    }

    @Test
    void statusUpdate() {
    }

    @Test
    void findAll() {
    }

    @Test
    void getReport() {
    }

    @Test
    void getRanking() {
    }
}
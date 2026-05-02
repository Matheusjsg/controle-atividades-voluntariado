package com.abcaa.sistema_atividades.mapperTest;


import com.abcaa.sistema_atividades.domain.entity.Activity;
import com.abcaa.sistema_atividades.domain.entity.Department;
import com.abcaa.sistema_atividades.domain.entity.Volunteer;
import com.abcaa.sistema_atividades.domain.enums.ActivityStatus;
import com.abcaa.sistema_atividades.dto.ActivityDTO;
import com.abcaa.sistema_atividades.mapper.ActivityMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
@DisplayName("ActivityMapper - Testes de conversão")
public class ActivityMapperTest {

    @InjectMocks
    private ActivityMapper mapper;


    @Test
    void shouldConvertDtoToEntity() {
        ActivityDTO dto = new ActivityDTO();
        dto.setDate(LocalDate.now());
        dto.setTitle("Limpeza");
        dto.setDescription("Mutirão");
        dto.setDurationMinutes(120);
        dto.setActivityStatus(ActivityStatus.PENDING);

        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);

        Activity activity = mapper.toEntity(dto, volunteer);

        assertEquals(dto.getDate(), activity.getDate());
        assertEquals(dto.getTitle(), activity.getTitle());
        assertEquals(dto.getDescription(), activity.getDescription());
        assertEquals(dto.getDurationMinutes(), activity.getDurationMinutes());
        assertEquals(volunteer, activity.getVolunteer());
        assertEquals(dto.getActivityStatus(), activity.getActivityStatus());
    }

       @Test
    void shouldConvertEntityToDto(){

        Department department = new Department();
        department.setName("Saúde");
        department.setId(2L);

        Volunteer volunteer = new Volunteer();
        volunteer.setId(5L);
        volunteer.setName("matheus");
        volunteer.setDepartment(department);


        Activity entity = new Activity();
        entity.setVolunteer(volunteer);
        entity.setDate(LocalDate.now());
        entity.setTitle("Limpeza");
        entity.setDescription("Limpeza de Abrigo");
        entity.setDurationMinutes(60);
        entity.setActivityStatus(ActivityStatus.APPROVED);



        ActivityDTO activityDto = mapper.toDTO(entity);

        assertEquals(entity.getDate(), activityDto.getDate());
        assertEquals(entity.getTitle(), activityDto.getTitle());
        assertEquals(entity.getDurationMinutes(), activityDto.getDurationMinutes());
        assertEquals(entity.getActivityStatus(), activityDto.getActivityStatus());
        assertEquals(entity.getVolunteer().getId(), activityDto.getVolunteerId());
        assertEquals(entity.getVolunteer().getName(), activityDto.getVolunteerName());
        assertEquals(entity.getVolunteer().getDepartment().getName(), activityDto.getDepartmentName());
        assertEquals(entity.getVolunteer().getDepartment().getId(), activityDto.getDepartmentId());

    }






}

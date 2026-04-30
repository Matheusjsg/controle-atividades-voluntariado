package com.abcaa.sistema_atividades.mapperTest;

import com.abcaa.sistema_atividades.domain.entity.Department;
import com.abcaa.sistema_atividades.domain.entity.Volunteer;
import com.abcaa.sistema_atividades.domain.enums.UserType;
import com.abcaa.sistema_atividades.dto.VolunteerDTO;
import com.abcaa.sistema_atividades.mapper.VolunteerMapper;
import com.abcaa.sistema_atividades.repository.DepartmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("VolunteerMapper")
public class VolunteerMapperTest {

    @InjectMocks
    private VolunteerMapper mapper;

    @Mock
    private DepartmentRepository departmentRepository;

    @Test
    @DisplayName("deve converter DTO para entidade com todos os campos mapeados")
    void shouldConvertDtoToEntity() {


        Department department = new Department();
        department.setId(1L);
        department.setName("Administração");

        VolunteerDTO dto = new VolunteerDTO();
        dto.setName("Matheus");
        dto.setEmail("testan@gmail.com");
        dto.setDepartmentId(department.getId());
        dto.setUserType(UserType.ADMIN);

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Volunteer volunteer = mapper.toEntity(dto);

        assertEquals(dto.getName(), volunteer.getName());
        assertEquals(dto.getEmail(), volunteer.getEmail());
        assertEquals(dto.getUserType(), volunteer.getUserType());
        assertEquals(dto.getDepartmentId(), volunteer.getDepartment().getId());


    }

    @Test
    @DisplayName("deve converter entidade para DTO com departmentId correto")
    void shouldConvertEntitytoDTO() {

        Department department = new Department();
        department.setId(1L);
        department.setName("Administração");

        Volunteer entity = new Volunteer();
        entity.setId(2L);
        entity.setName("Matheus");
        entity.setEmail("teste@matheus.com");
        entity.setDepartment(department);
        entity.setUserType(UserType.ADMIN);

        VolunteerDTO volunteerDTO = mapper.toDTO(entity);

        assertEquals(entity.getId(), volunteerDTO.getId());
        assertEquals(entity.getName(), volunteerDTO.getName());
        assertEquals(entity.getDepartment().getId(), volunteerDTO.getDepartmentId());
        assertEquals(entity.getUserType(), volunteerDTO.getUserType());

    }

    @Test
    @DisplayName("deve converter lista de entidades para lista de DTOs")
    void toDTOs() {
    }
}
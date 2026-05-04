package com.abcaa.sistema_atividades.service;

import com.abcaa.sistema_atividades.domain.entity.Department;
import com.abcaa.sistema_atividades.domain.entity.Volunteer;
import com.abcaa.sistema_atividades.domain.enums.UserType;
import com.abcaa.sistema_atividades.dto.VolunteerDTO;
import com.abcaa.sistema_atividades.mapper.VolunteerMapper;
import com.abcaa.sistema_atividades.repository.DepartmentRepository;
import com.abcaa.sistema_atividades.repository.VolunteerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {

@Mock
private VolunteerRepository volunteerRepository;

@Mock
private DepartmentRepository departmentRepository;

@Mock
private VolunteerMapper volunteerMapper;


@InjectMocks
private VolunteerService volunteerService;
private VolunteerDTO volunteerDTO;
private Department department;
private Volunteer volunteer;

    @BeforeEach
    void setUp() {

        department = new Department(1L, "Administrativo");

        volunteerDTO = new VolunteerDTO();
        volunteerDTO.setId(1L);
        volunteerDTO.setName("João Silva");
        volunteerDTO.setEmail("joao@test.com");
        volunteerDTO.setDepartmentId(department.getId());
        volunteerDTO.setUserType(UserType.VOLUNTEER);

        volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setName("João Silva");
        volunteer.setEmail("joao@test.com");
        volunteer.setPassword("encodedPassword");
        volunteer.setDepartment(department);
        volunteer.setUserType(UserType.VOLUNTEER);


    }

    @Test
    void shouldCreateVolunteerSuccessfully() {

        when(volunteerMapper.toEntity(volunteerDTO)).thenReturn(volunteer);
        when(volunteerRepository.save(volunteer)).thenReturn(volunteer);
        when(volunteerMapper.toDTO(volunteer)).thenReturn(volunteerDTO);

        VolunteerDTO result = volunteerService.create(volunteerDTO);

        assertNotNull(result);
        assertEquals(UserType.VOLUNTEER, result.getUserType());

        verify(volunteerMapper).toEntity(volunteerDTO);
        verify(volunteerRepository).save(volunteer);
        verify(volunteerMapper).toDTO(volunteer);


    }

    @Test
    void findAll() {



    }

    @Test
    void findById() {
    }

    @Test
    void shouldUpdateVolunteerSuccessfully() {
        Long id = volunteer.getId();
        Department newDepartment = new Department(2L, "Financeiro");

        VolunteerDTO editVolunteer = new VolunteerDTO();
        editVolunteer.setId(id);
        editVolunteer.setName("Maria Silva");
        editVolunteer.setEmail("maria@test.com");
        editVolunteer.setDepartmentId(newDepartment.getId());
        editVolunteer.setUserType(UserType.ADMIN);

        VolunteerDTO response = new VolunteerDTO();

        when(volunteerRepository.findById(id)).thenReturn(Optional.of(volunteer));
        when(departmentRepository.findById(newDepartment.getId())).thenReturn(Optional.of(newDepartment));
        when(volunteerRepository.save(volunteer)).thenReturn(volunteer);
        when(volunteerMapper.toDTO(volunteer)).thenReturn(response);


        VolunteerDTO result = volunteerService.volunteerUpdate(id, editVolunteer);


        assertNotNull(result);


        assertEquals("Maria Silva", volunteer.getName());
        assertEquals("maria@test.com", volunteer.getEmail());
        assertEquals(newDepartment, volunteer.getDepartment());
        assertEquals(UserType.ADMIN, volunteer.getUserType());

        verify(volunteerRepository).save(volunteer);


    }

    @Test
    void deleteVolunteer() {
    }

    @Test
    void updateUserType() {




    }
}
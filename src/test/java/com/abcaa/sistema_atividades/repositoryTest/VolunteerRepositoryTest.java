package com.abcaa.sistema_atividades.repositoryTest;

import com.abcaa.sistema_atividades.domain.entity.Volunteer;
import com.abcaa.sistema_atividades.domain.enums.UserType;
import com.abcaa.sistema_atividades.repository.VolunteerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@DisplayName("VolunteerRepository - Testes de persistência")
public class VolunteerRepositoryTest {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Test
    @DisplayName("Deve lançar exceção ao persistir voluntário sem departamento")
    void shouldThrowExceptionWhenPersistingVolunteerWithoutDepartment() {
        Volunteer volunteer = new Volunteer();
        volunteer.setName("João Silva");
        volunteer.setEmail("joao@test.com");
        volunteer.setPassword("senha123");
        volunteer.setUserType(UserType.VOLUNTEER);
        volunteer.setDepartment(null);

        assertThrows(DataIntegrityViolationException.class, () -> {
            volunteerRepository.saveAndFlush(volunteer);
        });
    }
}
package com.abcaa.sistema_atividades.service;

import com.abcaa.sistema_atividades.domain.entity.Volunteer;
import com.abcaa.sistema_atividades.domain.entity.VolunteerProfile;
import com.abcaa.sistema_atividades.dto.VolunteerProfileDTO;
import com.abcaa.sistema_atividades.repository.VolunteerProfileRepository;
import com.abcaa.sistema_atividades.repository.VolunteerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProfileService - Testes de Profiles")
class VolunteerProfileServiceTest {

    @Mock
    private VolunteerRepository volunteerRepository;

    @Mock
    private VolunteerProfileRepository profileRepository;

    @InjectMocks
    private VolunteerProfileService profileService;
    private VolunteerProfile profile;
    private Volunteer volunteer;
    private VolunteerProfileDTO profileDTO;

    @BeforeEach
    void setUp() {

        volunteer = new Volunteer();
        volunteer.setId(1L);

        profileDTO = new VolunteerProfileDTO();
        profileDTO.setPhone("1111-1111");
        profileDTO.setAddress("Rua do Destino");
        profileDTO.setNumber("210");
        profileDTO.setComplement("Atras do mercadinho");
        profileDTO.setBairro("Conquista");
        profileDTO.setCity("Fortaleza");
        profileDTO.setState("Ce");
        profileDTO.setZipCode("4172.00");
        profileDTO.setBirthDate(LocalDate.now());

        profile = new VolunteerProfile();
        profile.setId(10L);



    }


    @Test
    void save() {

        Long id =volunteer.getId();

        when(volunteerRepository.findById(id)).thenReturn(Optional.of(volunteer));
        when(profileRepository.findByVolunteerId(id)).thenReturn(Optional.of(profile));
        when(profileRepository.save(any(VolunteerProfile.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        VolunteerProfileDTO result = profileService.save(id, profileDTO);
        assertNotNull(result);

        assertEquals("210", profile.getNumber());
        assertEquals("1111-1111", profile.getPhone());
        assertEquals("Fortaleza", profile.getCity());

        verify(volunteerRepository).findById(id);
        verify(profileRepository).findByVolunteerId(id);
        verify(profileRepository).save(any(VolunteerProfile.class));
    }

    @Test
    void findByVolunteerId() {




    }
}
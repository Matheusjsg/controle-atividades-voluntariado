package com.abcaa.sistema_atividades.service;

import com.abcaa.sistema_atividades.dto.VolunteerProfileDTO;
import com.abcaa.sistema_atividades.domain.entity.Volunteer;
import com.abcaa.sistema_atividades.domain.entity.VolunteerProfile;
import com.abcaa.sistema_atividades.repository.VolunteerProfileRepository;
import com.abcaa.sistema_atividades.repository.VolunteerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VolunteerProfileService {

    private final VolunteerProfileRepository profileRepository;
    private final VolunteerRepository volunteerRepository;

    public VolunteerProfileService(VolunteerProfileRepository profileRepository, VolunteerRepository volunteerRepository) {
        this.profileRepository = profileRepository;
        this.volunteerRepository = volunteerRepository;
    }


    public VolunteerProfileDTO findByVolunteerId(Long volunteerId) {
        VolunteerProfile profile = profileRepository.findByVolunteerId(volunteerId)
                .orElseThrow(() -> new EntityNotFoundException("Perfil não encontrado."));
        return toDTO(profile);
    }


    @Transactional
    public VolunteerProfileDTO save(Long volunteerId, VolunteerProfileDTO dto){

        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado."));

        VolunteerProfile profile = profileRepository.findByVolunteerId(volunteerId)
                .orElseGet(VolunteerProfile::new);

        profile.setVolunteer(volunteer);
        updateProfileData(profile, dto);

        VolunteerProfile saved = profileRepository.save(profile);

        return toDTO(saved);
    }


    private VolunteerProfileDTO toDTO(VolunteerProfile profile) {
        VolunteerProfileDTO dto = new VolunteerProfileDTO();
        dto.setId(profile.getId());
        dto.setCpf(profile.getCpf());
        dto.setPhone(profile.getPhone());
        dto.setAddress(profile.getAddress());
        dto.setNumber(profile.getNumber());
        dto.setComplement(profile.getAddress());
        dto.setBairro(profile.getBairro());
        dto.setCity(profile.getCity());
        dto.setState(profile.getState());
        dto.setZipCode(profile.getZipCode());
        dto.setBirthDate(profile.getBirthDate());
        return dto;
    }


    private void updateProfileData(VolunteerProfile profile, VolunteerProfileDTO dto) {
        profile.setCpf(dto.getCpf());
        profile.setPhone(dto.getPhone());
        profile.setAddress(dto.getAddress());
        profile.setNumber(dto.getNumber());
        profile.setComplement(dto.getComplement());
        profile.setBairro(dto.getBairro());
        profile.setCity(dto.getCity());
        profile.setState(dto.getState());
        profile.setZipCode(dto.getZipCode());
        profile.setBirthDate(dto.getBirthDate());
    }
}

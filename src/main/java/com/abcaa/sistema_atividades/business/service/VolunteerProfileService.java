package com.abcaa.sistema_atividades.business.service;

import com.abcaa.sistema_atividades.business.dto.VolunteerProfileDTO;
import com.abcaa.sistema_atividades.business.entities.Volunteer;
import com.abcaa.sistema_atividades.business.entities.VolunteerProfile;
import com.abcaa.sistema_atividades.business.repositories.VolunteerProfileRepository;
import com.abcaa.sistema_atividades.business.repositories.VolunteerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VolunteerProfileService {

    private final VolunteerProfileRepository profileRepository;
    private final VolunteerRepository volunteerRepository;

    public VolunteerProfileService(VolunteerProfileRepository profileRepository, VolunteerRepository volunteerRepository) {
        this.profileRepository = profileRepository;
        this.volunteerRepository = volunteerRepository;
    }

    public VolunteerProfileDTO save(Long volunteerId, VolunteerProfileDTO dto) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado."));

        VolunteerProfile profile = profileRepository.findByVolunteerId(volunteerId)
                .orElse(new VolunteerProfile());

        profile.setVolunteer(volunteer);
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

        VolunteerProfile saved = profileRepository.save(profile);
        return toDTO(saved);
    }

    public VolunteerProfileDTO findByVolunteerId(Long volunteerId) {
        VolunteerProfile profile = profileRepository.findByVolunteerId(volunteerId)
                .orElseThrow(() -> new EntityNotFoundException("Perfil não encontrado."));
        return toDTO(profile);
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
}

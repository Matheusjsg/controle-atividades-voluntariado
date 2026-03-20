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
        profile.setLogradouro(dto.getLogradouro());
        profile.setNumero(dto.getNumero());
        profile.setComplemento(dto.getComplemento());
        profile.setBairro(dto.getBairro());
        profile.setCidade(dto.getCidade());
        profile.setEstado(dto.getEstado());
        profile.setCep(dto.getCep());
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
        dto.setLogradouro(profile.getLogradouro());
        dto.setNumero(profile.getNumero());
        dto.setComplemento(profile.getComplemento());
        dto.setBairro(profile.getBairro());
        dto.setCidade(profile.getCidade());
        dto.setEstado(profile.getEstado());
        dto.setCep(profile.getCep());
        dto.setBirthDate(profile.getBirthDate());
        return dto;
    }
}

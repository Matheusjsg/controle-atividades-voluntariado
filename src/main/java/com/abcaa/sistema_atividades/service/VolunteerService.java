package com.abcaa.sistema_atividades.service;

import com.abcaa.sistema_atividades.dto.PagedResponseDTO;
import com.abcaa.sistema_atividades.dto.VolunteerDTO;
import com.abcaa.sistema_atividades.domain.entity.Department;
import com.abcaa.sistema_atividades.domain.entity.Volunteer;
import com.abcaa.sistema_atividades.domain.enums.UserType;
import com.abcaa.sistema_atividades.mapper.VolunteerMapper;
import com.abcaa.sistema_atividades.repository.DepartmentRepository;
import com.abcaa.sistema_atividades.repository.VolunteerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final VolunteerMapper volunteerMapper;
    private final DepartmentRepository departmentRepository;

    public VolunteerService(VolunteerRepository volunteerRepository, VolunteerMapper volunteerMapper, DepartmentRepository departmentRepository) {
        this.volunteerRepository = volunteerRepository;
        this.volunteerMapper = volunteerMapper;
        this.departmentRepository = departmentRepository;
    }


    public VolunteerDTO create(VolunteerDTO dto){

        Volunteer volunteer = volunteerMapper.toEntity(dto);

        //CRIANDO VOLUNTARIO COMO PADRÃO !!!!TEMPORARIAMENTE!!!
        volunteer.setUserType(UserType.VOLUNTEER);

        volunteer = volunteerRepository.save(volunteer);

        return volunteerMapper.toDTO(volunteer);
    }


    public PagedResponseDTO<VolunteerDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Volunteer> result = volunteerRepository.findAll(pageable);

        List<VolunteerDTO> content = result.getContent()
                .stream()
                .map(volunteerMapper::toDTO)
                .collect(Collectors.toList());

        return new PagedResponseDTO<>(content, result.getNumber(),
                result.getSize(), result.getTotalElements(), result.getTotalPages());
    }


    public VolunteerDTO findById(Long id){

        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado."));

        return volunteerMapper.toDTO(volunteer);
    }



    public VolunteerDTO volunteerUpdate(Long id, VolunteerDTO dto){

        Volunteer existingVolunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado."));

        existingVolunteer.setName(dto.getName());
        existingVolunteer.setEmail(dto.getEmail());

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado."));

        existingVolunteer.setDepartment(department);
        existingVolunteer.setUserType(dto.getUserType());

        Volunteer updatedVolunteer = volunteerRepository.save(existingVolunteer);

         return volunteerMapper.toDTO(updatedVolunteer);

    }


    public void deleteVolunteer(Long id){
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado."));
        volunteerRepository.deleteById(volunteer.getId());
    }

    public VolunteerDTO updateUserType(Long id, UserType userType) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado."));
        volunteer.setUserType(userType);
        return volunteerMapper.toDTO(volunteerRepository.save(volunteer));
    }

}
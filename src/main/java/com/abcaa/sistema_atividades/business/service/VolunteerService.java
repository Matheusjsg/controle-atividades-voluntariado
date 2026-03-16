package com.abcaa.sistema_atividades.business.service;

import com.abcaa.sistema_atividades.business.dto.VolunteerDTO;
import com.abcaa.sistema_atividades.business.entities.Department;
import com.abcaa.sistema_atividades.business.entities.Volunteer;
import com.abcaa.sistema_atividades.business.enums.UserType;
import com.abcaa.sistema_atividades.business.mapper.VolunteerMapper;
import com.abcaa.sistema_atividades.business.repositories.DepartmentRepository;
import com.abcaa.sistema_atividades.business.repositories.VolunteerRepository;
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

        volunteer.setUserType(UserType.VOLUNTEER);

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department não encontrado"));


        volunteer = volunteerRepository.save(volunteer);

        return VolunteerMapper.toDTO(volunteer);
    }

    public List<VolunteerDTO> findAll(){

        return volunteerRepository.findAll()
                .stream()
                .map(VolunteerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public VolunteerDTO volunteerData(Long id){

        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Informações não encontrada."));
        return volunteerMapper.toDTO(volunteer);


    }

    public void deleteVolunteer(Long id){
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        volunteerRepository.deleteById(volunteer.getId());
    }

}
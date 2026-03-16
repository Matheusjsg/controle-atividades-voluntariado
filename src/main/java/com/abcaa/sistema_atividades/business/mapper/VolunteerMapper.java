package com.abcaa.sistema_atividades.business.mapper;


import com.abcaa.sistema_atividades.business.dto.VolunteerDTO;
import com.abcaa.sistema_atividades.business.entities.Department;
import com.abcaa.sistema_atividades.business.entities.Volunteer;
import com.abcaa.sistema_atividades.business.repositories.DepartmentRepository;
import org.springframework.stereotype.Component;


@Component
public class VolunteerMapper {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper mapper;
    public VolunteerMapper(DepartmentRepository departmentRepository, DepartmentMapper mapper) {
        this.departmentRepository = departmentRepository;
        this.mapper = mapper;
    }

    public Volunteer toEntity(VolunteerDTO dto){

       Department department = departmentRepository.findById(dto.getDepartmentId())
               .orElseThrow(() -> new RuntimeException("Department não encontrado") );



        Volunteer volunteer = new Volunteer();

        volunteer.setId(dto.getId());
        volunteer.setName(dto.getName());
        volunteer.setEmail(dto.getEmail());
        volunteer.setDepartment(department);
        volunteer.setUserType(dto.getUserType());

        return volunteer;
    }

    public static VolunteerDTO toDTO(Volunteer volunteer){

        VolunteerDTO dto = new VolunteerDTO();

        dto.setId(volunteer.getId());
        dto.setName(volunteer.getName());
        dto.setEmail(volunteer.getEmail());
        dto.setDepartmentId(volunteer.getDepartment().getId());
        dto.setUserType(volunteer.getUserType());

        return dto;
    }



}






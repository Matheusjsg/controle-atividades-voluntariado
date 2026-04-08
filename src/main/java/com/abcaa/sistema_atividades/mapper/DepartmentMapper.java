package com.abcaa.sistema_atividades.mapper;

import com.abcaa.sistema_atividades.dto.DepartmentDTO;
import com.abcaa.sistema_atividades.domain.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentDTO dto){

        Department department = new Department();

        department.setId(dto.getId());
        department.setName(dto.getName());

        return department;
    }

    public DepartmentDTO toDTO(Department department){

        DepartmentDTO dto = new DepartmentDTO();

        dto.setId(department.getId());
        dto.setName(department.getName());

        return dto;
    }


}

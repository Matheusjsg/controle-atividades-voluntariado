package com.abcaa.sistema_atividades.business.service;

import com.abcaa.sistema_atividades.business.dto.DepartmentDTO;
import com.abcaa.sistema_atividades.business.entities.Department;
import com.abcaa.sistema_atividades.business.mapper.DepartmentMapper;
import com.abcaa.sistema_atividades.business.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public DepartmentDTO create(DepartmentDTO dto){

        Department departmentEntity = departmentMapper.toEntity(dto);

        Department saved = departmentRepository.save(departmentEntity);

        return departmentMapper.toDTO(saved);
    }

    public List<DepartmentDTO> findAll(){

        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toDTO)
                .collect(Collectors.toList());
    }

}

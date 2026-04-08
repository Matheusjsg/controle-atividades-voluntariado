package com.abcaa.sistema_atividades.service;

import com.abcaa.sistema_atividades.dto.DepartmentDTO;
import com.abcaa.sistema_atividades.domain.entity.Department;
import com.abcaa.sistema_atividades.mapper.DepartmentMapper;
import com.abcaa.sistema_atividades.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
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


    public DepartmentDTO departmentUpdate(Long id, DepartmentDTO dto){

        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado."));

        existingDepartment.setName(dto.getName());

        Department updatedDepartment = departmentRepository.save(existingDepartment);

        return departmentMapper.toDTO(updatedDepartment);

    }

    public void delete(Long id) {
                Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado."));

        departmentRepository.deleteById(department.getId());

    }

    public DepartmentDTO findById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado."));

        return departmentMapper.toDTO(department);
    }


    public List<DepartmentDTO> findAll(){

        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toDTO)
                .collect(Collectors.toList());
    }

}

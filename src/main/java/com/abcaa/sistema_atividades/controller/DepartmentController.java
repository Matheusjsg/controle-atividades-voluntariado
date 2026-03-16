package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.dto.DepartmentDTO;
import com.abcaa.sistema_atividades.business.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/create")
    public DepartmentDTO create(@RequestBody DepartmentDTO dto){
        return departmentService.create(dto);
    }

    @GetMapping("/list")
    public List<DepartmentDTO> findAll(){
        return departmentService.findAll();
    }

}
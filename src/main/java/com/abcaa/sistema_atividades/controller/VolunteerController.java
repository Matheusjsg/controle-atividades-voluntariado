package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.dto.VolunteerDTO;
import com.abcaa.sistema_atividades.business.entities.Department;
import com.abcaa.sistema_atividades.business.service.VolunteerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping("/create")
    public VolunteerDTO create(@RequestBody VolunteerDTO dto){
        return volunteerService.create(dto);
    }

    @GetMapping("/list")
    public List<VolunteerDTO> findAll(){
        return volunteerService.findAll();
    }

}
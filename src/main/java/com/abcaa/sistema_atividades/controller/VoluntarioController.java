package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.dto.VoluntarioDTO;
import com.abcaa.sistema_atividades.business.entities.Setor;
import com.abcaa.sistema_atividades.business.service.VoluntarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voluntarios")
public class VoluntarioController {

    private final VoluntarioService voluntarioService;

    public VoluntarioController(VoluntarioService voluntarioService) {
        this.voluntarioService = voluntarioService;
    }

    @PostMapping("/criar")
    public VoluntarioDTO criar(@RequestBody VoluntarioDTO dto, Setor setorDTO){
        return voluntarioService.criar(dto, setorDTO);
    }

    @GetMapping("/lista")
    public List<VoluntarioDTO> listar(){
        return voluntarioService.listar();
    }

}
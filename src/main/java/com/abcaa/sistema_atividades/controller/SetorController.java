package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.dto.SetorDTO;
import com.abcaa.sistema_atividades.business.service.SetorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setores")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @PostMapping("/criar")
    public SetorDTO criar(@RequestBody SetorDTO dto){
        return setorService.criar(dto);
    }

    @GetMapping("/lista")
    public List<SetorDTO> listar(){
        return setorService.listar();
    }

}
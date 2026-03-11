package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.dto.AtividadeDTO;
import com.abcaa.sistema_atividades.business.service.AtividadeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    private final AtividadeService atividadeService;

    public AtividadeController(AtividadeService atividadeService) {
        this.atividadeService = atividadeService;
    }

    @PostMapping("/criar")
    public AtividadeDTO criar(@RequestBody AtividadeDTO dto){
        return atividadeService.criar(dto);
    }

    @GetMapping("/listar")
    public List<AtividadeDTO> listar(){
        return atividadeService.listar();
    }

}
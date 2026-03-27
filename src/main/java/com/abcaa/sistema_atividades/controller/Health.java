package com.abcaa.sistema_atividades.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class Health {

//classe para verificar se a aplicação está rodando, pode ser acessada em /helt

    @Operation (summary = "Verificar saúde da aplicação", description = "Endpoint para verificar se a aplicação está rodando corretamente")
    @GetMapping("/helth")
    public String healt(){
        return "ok";
    }
}

package com.abcaa.sistema_atividades.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="iniciar o servidor no render", description="verificar se a aplicação está rodando" )
@RequestMapping("/health")

public class HealthController {

        @GetMapping("/ping")
        public String healt(){

            return "ok";

            }
}

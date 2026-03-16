package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.dto.VolunteerDTO;
import com.abcaa.sistema_atividades.business.entities.Department;
import com.abcaa.sistema_atividades.business.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteer")
@Tag(name = "Voluntários", description = "Gerenciamento de voluntários da organização")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping("/create")
    @Operation(summary = "Cadastrar novo voluntário", description = "Cria um novo cadastro de voluntário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voluntário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Setor não encontrado")})
    public VolunteerDTO create(@RequestBody VolunteerDTO dto){
        return volunteerService.create(dto);
    }




    @Operation(summary = "Listar todos os voluntários", description = "Retorna a lista completa de voluntários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping("/list")
    public List<VolunteerDTO> findAll(){
        return volunteerService.findAll();
    }

}
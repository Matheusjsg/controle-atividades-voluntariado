package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.dto.ActivityDTO;
import com.abcaa.sistema_atividades.business.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
@Tag(name = "Atividades", description = "Gerenciamento de atividades de voluntariado")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/create")
    @Operation(summary = "Registrar nova atividade", description = "Cria um novo registro de atividade realizada por um voluntário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Atividade registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado")})
    public ActivityDTO create(@RequestBody ActivityDTO dto){
        return activityService.create(dto);
    }


    @Operation(summary = "Listar todas as atividades", description = "Retorna a lista completa de atividades registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")})
    @GetMapping("/list")
    public List<ActivityDTO> findAll(){
                return activityService.findAll();
    }

}
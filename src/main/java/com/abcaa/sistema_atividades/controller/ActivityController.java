package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.dto.ActivityDTO;
import com.abcaa.sistema_atividades.business.enums.ActivityStatus;
import com.abcaa.sistema_atividades.business.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ActivityDTO> create(@Valid @RequestBody ActivityDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(activityService.create(dto));

    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualizar atividade", description = "Atualiza os dados de uma atividade existente")
            @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividade atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Atividade não encontrada")})
    public ResponseEntity<ActivityDTO> update(@PathVariable Long id, @Valid @RequestBody ActivityDTO activityDTO){
         return ResponseEntity.ok(activityService.activityUpdate(id, activityDTO));


    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Excluir atividade", description = "Remove um registro de atividade do sistema")
            @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Atividade excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atividade não encontrada")})
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id){
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/volunteer/{volunteerId}")
    @Operation(summary ="Listar atividades do voluntario", description = "Busca atividades criadas pelo voluntário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado")})
    public ResponseEntity<List<ActivityDTO>> findByAcity(@PathVariable Long id){
        return ResponseEntity.ok(activityService.findByVolunteerId(id));
    }



    @GetMapping("/list/{id}")
    @Operation(summary = "Buscar atividade por ID", description = "Retorna os dados de uma atividade específica pelo seu ID")
            @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividade retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atividade não encontrada")})
    public ResponseEntity<ActivityDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(activityService.findById(id));
    }



    @GetMapping("/listAll")
    @Operation(summary = "Listar todas as atividades", description = "Retorna a lista completa de atividades registradas")
            @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")})
    public ResponseEntity<List<ActivityDTO>> findAll(){
        return ResponseEntity.ok(activityService.findAll());
    }



    @GetMapping("/status/{status}")
        @Operation(summary = "Listar atividades por status")
    public ResponseEntity<List<ActivityDTO>> findByStatus(@PathVariable ActivityStatus status){
        return ResponseEntity.ok(activityService.findActivitiesByStatus(status));
    }




}
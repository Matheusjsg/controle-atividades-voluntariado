package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.dto.VolunteerDTO;
import com.abcaa.sistema_atividades.business.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<VolunteerDTO> volunteerCreate(@RequestBody VolunteerDTO dto){
                return ResponseEntity.status(HttpStatus.CREATED).body(volunteerService.create(dto));
    }


    @GetMapping("/activity/{id}")
    @Operation(summary ="Listar atividades do voluntario", description = "Busca atividades criadas pelo voluntário")
            @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado")})
    public ResponseEntity<List<ActivityDTO>> findByAcity(@PathVariable Long id){
        return ResponseEntity.ok(activityService.findByVolunteerId(id));
    }

    @Operation  (summary = "Excluir voluntário", description = "Remove um voluntário do sistema com base no ID informado")
    @DeleteMapping("/delete/{id}")
            @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Voluntário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado")})
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id){
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Listar todos os voluntários", description = "Retorna a lista completa de voluntários cadastrados")
            @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
    @GetMapping("/list")
    public List<VolunteerDTO> findAll(){
        return volunteerService.findAll();
    public ResponseEntity<List<VolunteerDTO>> findAll(){
        return ResponseEntity.ok(volunteerService.findAll());
    }



    @Operation(summary = "Buscar voluntário por ID", description = "Retorna os dados de um voluntário específico com base no ID informado")
            @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voluntário retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado") })
    @GetMapping("/{id}")
    public ResponseEntity<VolunteerDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(volunteerService.findById(id));
    }


    @Operation(summary = "Atualizar dados do voluntário", description = "Atualiza as informações de um voluntário existente")
            @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voluntário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Voluntário ou setor não encontrado")})
    @PutMapping("/update/{id}")
    public ResponseEntity<VolunteerDTO> volunteerUpdate(@PathVariable Long id, @RequestBody VolunteerDTO dto){
        return ResponseEntity.ok(volunteerService.volunteerUpdate(id, dto));
    }





}
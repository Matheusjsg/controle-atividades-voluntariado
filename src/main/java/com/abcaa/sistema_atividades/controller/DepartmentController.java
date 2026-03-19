package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.dto.DepartmentDTO;
import com.abcaa.sistema_atividades.business.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@Tag(name = "Setores", description = "Gerenciamento de setores da organização")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/create")
    @Operation(summary = "Cadastrar novo setor", description = "Cria um novo setor na organização")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Setor cadastrado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos")})

    public ResponseEntity<DepartmentDTO> create(@RequestBody DepartmentDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.create(dto));
    }



    @PutMapping("/update/{id}")
    @Operation(summary = "Atualizar dados do setor", description = "Atualiza as informações de um setor existente")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Setor atualizado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
    @ApiResponse(responseCode = "404", description = "Setor não encontrado")})
    public ResponseEntity<DepartmentDTO> update(@PathVariable Long id, @RequestBody DepartmentDTO dto){
        return ResponseEntity.ok(departmentService.departmentUpdate(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Excluir setor", description = "Remove um setor cadastrado com base no ID informado")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Setor excluído com sucesso"),
    @ApiResponse(responseCode = "404", description = "Setor não encontrado")})
        public ResponseEntity<Void> delete(@PathVariable Long id){
                 departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar setor por ID", description = "Retorna os dados de um setor específico com base no ID informado")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Setor retornado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Setor não encontrado")})
    public ResponseEntity<DepartmentDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(departmentService.findById(id));
    }


    @GetMapping("/list")
    @Operation(summary = "Listar todos os setores", description = "Retorna a lista completa de setores cadastrados")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")})
        public ResponseEntity<List<DepartmentDTO>> findAll(){
                return ResponseEntity.ok(departmentService.findAll());
    }

}
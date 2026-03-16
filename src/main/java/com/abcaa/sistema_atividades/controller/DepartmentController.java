package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.dto.DepartmentDTO;
import com.abcaa.sistema_atividades.business.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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


    @Operation(summary = "Cadastrar novo setor", description = "Cria um novo setor na organização")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Setor cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    @PostMapping("/create")
    public DepartmentDTO create(@RequestBody DepartmentDTO dto){
        return departmentService.create(dto);
    }



    @Operation(summary = "Listar todos os setores", description = "Retorna a lista completa de setores cadastrados")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")})
    @GetMapping("/list")
    public List<DepartmentDTO> findAll(){
        return departmentService.findAll();
    }

}
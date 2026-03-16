package com.abcaa.sistema_atividades.business.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de um setor da organização")
public class DepartmentDTO {

    @Schema(description = "ID do setor", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nome do setor", example = "Administrativo", required = true)
    private String name;

    public DepartmentDTO() {}

    public DepartmentDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.abcaa.sistema_atividades.dto;

import com.abcaa.sistema_atividades.domain.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

public class VolunteerDTO {
    @Schema(description = "ID do voluntário", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nome completo do voluntário", example = "João Silva", required = true)
    private String name;

    @Schema(description = "Email do voluntário", example = "joao.silva@email.com")
    private String email;

    @Schema(description = "ID do setor onde o voluntário atua", example = "2", required = true)
    private Long departmentId;

    @Schema(description = "Tipo de usuário no sistema", example = "VOLUNTARIO")
    private UserType userType;


    public VolunteerDTO() {
    }

    public VolunteerDTO(Long id, String name, String email, Long departmentId, UserType userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.departmentId = departmentId;
        this.userType = userType;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
package com.abcaa.sistema_atividades.business.dto;

import com.abcaa.sistema_atividades.business.enums.TipoUsuario;

public class VoluntarioDTO {
    private Long id;

    private String nome;

    private String email;

    private Long setorId;

    private TipoUsuario tipoUsuario;


    public VoluntarioDTO() {
    }

    public VoluntarioDTO(Long id, String nome, String email, Long setorId, TipoUsuario tipoUsuario) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.setorId = setorId;
        this.tipoUsuario = tipoUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getSetorId() {
        return setorId;
    }

    public void setSetorId(Long setorId) {
        this.setorId = setorId;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
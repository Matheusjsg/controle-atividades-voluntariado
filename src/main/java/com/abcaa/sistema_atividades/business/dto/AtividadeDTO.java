package com.abcaa.sistema_atividades.business.dto;

import java.time.LocalDate;

public class AtividadeDTO {
    private Long id;

    private LocalDate data;

    private String descricao;

    private Integer tempoMinutos;

    private Long voluntarioId;

    private String status;

    public AtividadeDTO() {}

    public AtividadeDTO(LocalDate data, String descricao, Integer tempoMinutos, Long voluntarioId, String status) {
        this.data = data;
        this.descricao = descricao;
        this.tempoMinutos = tempoMinutos;
        this.voluntarioId = voluntarioId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getTempoMinutos() {
        return tempoMinutos;
    }

    public void setTempoMinutos(Integer tempoMinutos) {
        this.tempoMinutos = tempoMinutos;
    }

    public Long getVoluntarioId() {
        return voluntarioId;
    }

    public void setVoluntarioId(Long voluntarioId) {
        this.voluntarioId = voluntarioId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.abcaa.sistema_atividades.business.entities;

import com.abcaa.sistema_atividades.business.enums.StatusAtividade;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name= "tb_atividade")
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    private String descricao;

    private Integer tempoMinutos;

    @ManyToOne
    @JoinColumn(name = "voluntario_id")
    private Voluntario voluntario;

    @Enumerated(EnumType.STRING)
    private StatusAtividade status;


    public Atividade() {}

    public Atividade(Long id, LocalDate data, String descricao, Integer tempoMinutos, Voluntario voluntario, StatusAtividade status) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.tempoMinutos = tempoMinutos;
        this.voluntario = voluntario;
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

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
    }

    public StatusAtividade getStatus() {
        return status;
    }

    public void setStatus(StatusAtividade status) {
        this.status = status;
    }
}


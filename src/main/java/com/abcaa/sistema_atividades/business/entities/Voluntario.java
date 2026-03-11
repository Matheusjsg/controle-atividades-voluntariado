package com.abcaa.sistema_atividades.business.entities;

import com.abcaa.sistema_atividades.business.enums.TipoUsuario;
import jakarta.persistence.*;


@Entity
@Table(name= "tb_voluntario")
public class Voluntario {

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private Long id;

    @Column(name= "nome")
    private String nome;

    @Column(name= "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor setor;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;


    public Voluntario() {
    }

    public Voluntario(Long id, String nome, String email, Setor setor, TipoUsuario tipoUsuario) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.setor = setor;
        this.tipoUsuario = tipoUsuario;
    }


    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
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
}

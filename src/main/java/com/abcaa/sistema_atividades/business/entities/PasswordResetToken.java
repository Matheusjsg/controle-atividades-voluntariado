package com.abcaa.sistema_atividades.business.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name= "tb_password_reset_token")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String token;

    private LocalDateTime expiration;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteerId;


    public PasswordResetToken() {
    }

    public PasswordResetToken(UUID id, String token, LocalDateTime expiration, Volunteer volunteerId) {
        this.id = id;
        this.token = token;
        this.expiration = expiration;
        this.volunteerId = volunteerId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public Volunteer getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Volunteer volunteerId) {
        this.volunteerId = volunteerId;
    }
}

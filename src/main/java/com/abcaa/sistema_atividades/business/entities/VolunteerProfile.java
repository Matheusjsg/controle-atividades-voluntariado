package com.abcaa.sistema_atividades.business.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_volunteer_profile")
public class VolunteerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "volunteer_id", unique = true, nullable = false)
    private Volunteer volunteer;

    private String cpf;
    private String phone;

    @Column(name="logradouro")
    private String address;
    @Column(name="numero")
    private String number;
    @Column(name="complemento")
    private String complement;
    private String bairro;
    @Column(name="cidade")
    private String city;
    @Column(name="estado")
    private String state;
    @Column(name="cep")
    private String zipCode;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    public VolunteerProfile() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Volunteer getVolunteer() { return volunteer; }
    public void setVolunteer(Volunteer volunteer) { this.volunteer = volunteer; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public String getComplement() { return complement; }
    public void setComplement(String complement) { this.complement = complement; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
}

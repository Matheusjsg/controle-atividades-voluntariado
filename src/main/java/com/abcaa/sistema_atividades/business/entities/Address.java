package com.abcaa.sistema_atividades.business.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "road")
    private String road;
    @Column(name = "number")
    private Long number;
    @Column (name = "supplement", length = 10)
    private String supplement;
    @Column (name = "city", length = 150)
    private String city;
    @Column (name = "state", length = 2)
    private String state;
    @Column (name = "zipcode", length = 9)
    private String zipcode;


}
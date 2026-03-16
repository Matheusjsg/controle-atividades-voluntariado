package com.abcaa.sistema_atividades.business.entities;

import com.abcaa.sistema_atividades.business.enums.UserType;
import jakarta.persistence.*;


@Entity
@Table(name= "tb_volunteer")
public class Volunteer {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "name")
    private String name;

    @Column(name= "email")
    private String email;

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;


    public Volunteer() {
    }

    public Volunteer(Long id, String name, String email, Department department, UserType userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}

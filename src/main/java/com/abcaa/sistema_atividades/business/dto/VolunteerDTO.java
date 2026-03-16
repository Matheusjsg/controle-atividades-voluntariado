package com.abcaa.sistema_atividades.business.dto;

import com.abcaa.sistema_atividades.business.enums.UserType;

public class VolunteerDTO {
    private Long id;

    private String name;

    private String email;

    private Long departmentId;

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
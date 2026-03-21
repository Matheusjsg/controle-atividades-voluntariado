package com.abcaa.sistema_atividades.business.dto;

public class TokenDTO {

    private String token;
    private String name;
    private Long volunteerId;
    private Long departmentId;
    private String userType;

    public TokenDTO(String token, String name, Long volunteerId, Long departmentId, String userType) {
        this.token = token;
        this.name = name;
        this.volunteerId = volunteerId;
        this.departmentId = departmentId;
        this.userType = userType;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
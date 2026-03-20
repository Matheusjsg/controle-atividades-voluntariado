package com.abcaa.sistema_atividades.business.dto;

public class TokenDTO {

    private String token;
    private String name;
    private String userType;

    public TokenDTO(String token, String name, String userType) {
        this.token = token;
        this.name = name;
        this.userType = userType;
    }

    public String getToken() { return token; }
    public String getName() { return name; }
    public String getUserType() { return userType; }
}

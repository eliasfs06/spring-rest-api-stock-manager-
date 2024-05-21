package com.eliasfs06.spring.restapi.stock.manager.model.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthenticationDTO {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

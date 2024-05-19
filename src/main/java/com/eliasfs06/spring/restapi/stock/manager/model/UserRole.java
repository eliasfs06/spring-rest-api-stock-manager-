package com.eliasfs06.spring.restapi.stock.manager.model;

public enum UserRole {

    ADMIN("admin"),
    USER("user");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

}

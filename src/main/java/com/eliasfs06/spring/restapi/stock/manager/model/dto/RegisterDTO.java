package com.eliasfs06.spring.restapi.stock.manager.model.dto;

import com.eliasfs06.spring.restapi.stock.manager.model.Person;
import com.eliasfs06.spring.restapi.stock.manager.model.User;
import com.eliasfs06.spring.restapi.stock.manager.model.enums.UserRole;

public class RegisterDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private UserRole userRole;

    public Person toPerson(){
        Person person = new Person();
        person.setName(this.getName());
        person.setEmail(this.getEmail());

        return person;
    }

    public User toUser(){
        User user = new User();
        user.setUsername(this.getUsername());
        user.setPassword(this.getPassword());
        user.setUserRole(this.getUserRole());
        return user;
    }

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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}

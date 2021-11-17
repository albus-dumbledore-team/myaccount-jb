package com.bootcamp.demo.controller.dtos;

import javax.validation.constraints.Pattern;

public class UpdatePasswordDTO {
    @Pattern(regexp = "^[a-zA-Z0-9]+")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9@#!?]{6,}")
    private String password;

    public UpdatePasswordDTO() {
    }

    public UpdatePasswordDTO(String username, String password) {
        this.username = username;
        this.password = password;
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
}

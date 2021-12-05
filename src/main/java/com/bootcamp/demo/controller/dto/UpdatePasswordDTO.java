package com.bootcamp.demo.controller.dto;

import javax.validation.constraints.Pattern;

public class UpdatePasswordDTO {
    @Pattern(regexp = "^[a-zA-Z0-9]+")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9@#!?]{6,}")
    private String oldPassword;
    @Pattern(regexp = "^[a-zA-Z0-9@#!?]{6,}")
    private String newPassword;
    @Pattern(regexp = "^[a-zA-Z0-9@#!?]{6,}")
    private String confirmNewPassword;

    public UpdatePasswordDTO() {
    }

    public UpdatePasswordDTO(String username, String oldPassword, String newPassword, String confirmNewPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

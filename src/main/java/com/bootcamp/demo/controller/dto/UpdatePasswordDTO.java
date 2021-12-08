package com.bootcamp.demo.controller.dto;

import javax.validation.constraints.Pattern;

public class UpdatePasswordDTO {
//    @Pattern(regexp = "^[a-zA-Z0-9]+")
    private String email;
    @Pattern(regexp = "^[a-zA-Z0-9@#!?]{6,}")
    private String oldPassword;
    @Pattern(regexp = "^[a-zA-Z0-9@#!?]{6,}")
    private String newPassword;
    @Pattern(regexp = "^[a-zA-Z0-9@#!?]{6,}")
    private String confirmNewPassword;

    public UpdatePasswordDTO() {
    }

    public UpdatePasswordDTO(String email, String oldPassword, String newPassword, String confirmNewPassword) {
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

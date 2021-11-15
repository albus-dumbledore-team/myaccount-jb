package com.bootcamp.demo.model;

import java.time.LocalDate;
import java.time.Period;

public class Account {
    private String name;
    private String email;
    private String username;
    private String password;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;

    public Account(String name, String email, String username, String password, String phoneNumber, String address, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }

    public Account(){

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isValidName(String name){
        for (char c: name.toCharArray()){
            if (Character.isDigit(c))
                return false;
        }
        return true;
    }

    public boolean isValidDOB(LocalDate dateOfBirth){
        Period period = Period.between(dateOfBirth, LocalDate.now());
        if(period.getYears() > 18)
            return true;
        else
            return false;
    }

    public boolean isValidPhoneNumber(String phoneNumber){
        if(phoneNumber.length() != 10)
            return false;

        for (char c: phoneNumber.toCharArray()){
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

}

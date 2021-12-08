package com.bootcamp.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private String dateOfBirth;
    @JsonIgnore
    private ArrayList<Promotion> promotions = new ArrayList<>();

    public Account(String name, String email, String password, String phoneNumber, String address, String dateOfBirth) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }

    public Account() {

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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void addPromotion(Promotion promotion) {
        this.promotions.add(promotion);
    }

    public List<Promotion> getPromotions() {
        return this.promotions;
    }
}
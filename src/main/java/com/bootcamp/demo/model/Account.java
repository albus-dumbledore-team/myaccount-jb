package com.bootcamp.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String name;
    private String email;
    private String username;
    private String password;
    private String phoneNumber;
    private String address;
    private String dateOfBirth;
    @JsonIgnore
    private ArrayList<Promotion> promotions = new ArrayList<>();

    public Account(String name, String email, String username, String password, String phoneNumber, String address, String dateOfBirth) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
//        promotions = new ArrayList<>();
        addPromotion(new Promotion("1234",1,true,true));
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void addPromotion(Promotion promotion){
        this.promotions.add(promotion);
    }
    public List<Promotion> getPromotions(){
        return this.promotions;
    }
}
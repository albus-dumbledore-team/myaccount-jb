package com.bootcamp.demo.model;

public class AccountDetails extends Account{
    private String name;
    private String email;
    private String username;
    private String phoneNumber;
    private String address;
    private String dateOfBirth;
    public AccountDetails(String name,String email,String username,String phoneNumber,String address,String dateOfBirth){
        this.name=name;
        this.email=email;
        this.username=username;
        this.phoneNumber=phoneNumber;
        this.address=address;
        this.dateOfBirth=dateOfBirth;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

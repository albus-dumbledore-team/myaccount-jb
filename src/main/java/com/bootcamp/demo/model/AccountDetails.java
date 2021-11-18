package com.bootcamp.demo.model;

public class AccountDetails {
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
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }

}

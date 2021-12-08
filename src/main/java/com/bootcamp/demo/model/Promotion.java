package com.bootcamp.demo.model;

public class Promotion {
    private String code;
    private int amount;
    private Availability state;
    private DiscountType type;

    public enum Availability {
        ACTIVE,
        SUSPENDED,
        EXPIRED
    }

    public enum DiscountType {
        FLAT,
        PERCENTAGE
    }

    public Promotion(String code, int amount, Availability state, DiscountType type) {
        this.code = code;
        this.amount = amount;
        this.state = state;
        this.type = type;
    }

    public Promotion() {
    }

    public String getCode() {
        return code;
    }

    public Availability getState() {
        return state;
    }

    public void setState(Availability state) {
        this.state = state;
    }

    public DiscountType getType() {
        return type;
    }

    public void setType(DiscountType type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }


}

package com.bootcamp.demo.model;

public class Promotion {
    public String getCode() {
        return code;
    }

    private String code;
    private int amount;
    private boolean isFlatAmount;
    private boolean enabled;

    public Promotion(String code, int amount, boolean enabled, boolean isFlatAmount) {
        this.code = code;
        this.amount = amount;
        this.enabled = enabled;
        this.isFlatAmount = isFlatAmount;
    }
    public Promotion(){}

    public int getAmount() {
        return amount;
    }

    public boolean isFlatAmount() {
        return isFlatAmount;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public void setFlatAmount(boolean flatAmount){this.isFlatAmount = flatAmount;}
}

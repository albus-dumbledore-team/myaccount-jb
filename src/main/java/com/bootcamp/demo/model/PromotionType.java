package com.bootcamp.demo.model;

public class PromotionType {
    public String getId() {
        return id;
    }

    private final String id;
    final private int amount;
    final private boolean isFlatAmount;
    private boolean enabled;

    public PromotionType(String id, int amount, boolean enabled, boolean isFlatAmount) {
        this.id = id;
        this.amount = amount;
        this.enabled = enabled;
        this.isFlatAmount = isFlatAmount;
    }

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
}

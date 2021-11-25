package com.bootcamp.demo.model;

public class Promotion {
    final private int amount;
    final private boolean isFlatAmount,isPublic;
    private boolean enabled;
    final private String code;

    public Promotion(int amount, boolean enabled, boolean isFlatAmount, boolean isPublic, String code) {
        this.amount = amount;
        this.enabled = enabled;
        this.isFlatAmount = isFlatAmount;
        this.isPublic = isPublic;
        this.code = code;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isFlatAmount() {
        return isFlatAmount;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getCode() {
        return code;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

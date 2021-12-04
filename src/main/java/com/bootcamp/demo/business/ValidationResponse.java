package com.bootcamp.demo.business;

import java.util.ArrayList;

public class ValidationResponse {
    private ArrayList<String> messages;
    private boolean isValid;

    public ValidationResponse() {
        this.messages = new ArrayList<String>();
        this.isValid = true;
    }

    public void addMessage(String message) {
        if (this.messages == null) {
            this.messages = new ArrayList<String>();
        }
        this.messages.add(message);
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(boolean errorCode) {
        this.isValid = errorCode;
    }
}

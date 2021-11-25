package com.bootcamp.demo.business;

import java.util.ArrayList;

public class ValidationResponse {
    private ArrayList<String> messages;
    private boolean errorCode;

    public ValidationResponse(){
        this.messages = new ArrayList<String>();
        this.errorCode = false;
    }

    public void addMessage(String message) {
        if(this.messages == null) {
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

    public boolean getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(boolean errorCode) {
        this.errorCode = errorCode;
    }
}

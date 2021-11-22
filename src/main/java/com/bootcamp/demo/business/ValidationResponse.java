package com.bootcamp.demo.business;

import java.util.ArrayList;

public class ValidationResponse {
    private ArrayList<String> messages;
    private int errorCode;

    public ValidationResponse(){
        this.messages = new ArrayList<String>();
        this.errorCode = 200;
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

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}

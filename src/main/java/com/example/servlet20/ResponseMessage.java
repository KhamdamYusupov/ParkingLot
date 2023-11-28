package com.example.servlet20;

public class ResponseMessage {
    private String message;
    public ResponseMessage() {
    }
    public ResponseMessage(String errorMessage) {
        this.message = errorMessage;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return "ErrorMessage{" +
                "errorMessage='" + message + '\'' +
                '}';
    }
}

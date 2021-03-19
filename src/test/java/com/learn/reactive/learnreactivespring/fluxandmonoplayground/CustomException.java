package com.learn.reactive.learnreactivespring.fluxandmonoplayground;

public class CustomException extends Throwable {

    private String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

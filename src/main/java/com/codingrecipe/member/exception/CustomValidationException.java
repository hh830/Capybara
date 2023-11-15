package com.codingrecipe.member.exception;

public class CustomValidationException extends RuntimeException{
    private final int status;

    public CustomValidationException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}

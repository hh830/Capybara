package com.codingrecipe.member.exception;

public class InvalidRegistrationException extends RuntimeException {
    public InvalidRegistrationException(String message) {
        super(message);
    }
}

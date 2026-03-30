package com.example.springbootstarter.exception;

public class EmailUnverifiedException extends RuntimeException {
    public EmailUnverifiedException(String message) {
        super(message);
    }
}

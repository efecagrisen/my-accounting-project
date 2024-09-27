package com.ecs.exception;

public class CompanyNotFoundException extends RuntimeException{
    public CompanyNotFoundException(String message) {
        super(message);
    }
}

package com.ecs.exception;

public class InvoiceProductNotFoundException extends RuntimeException{
    public InvoiceProductNotFoundException(String message) {
        super(message);
    }
}

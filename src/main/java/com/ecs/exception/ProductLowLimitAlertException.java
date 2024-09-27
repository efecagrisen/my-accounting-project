package com.ecs.exception;

public class ProductLowLimitAlertException extends RuntimeException{
    public ProductLowLimitAlertException(String message) {
        super(message);
    }
}

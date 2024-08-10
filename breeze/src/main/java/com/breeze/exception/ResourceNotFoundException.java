package com.breeze.exception;

public class ResourceNotFoundException extends BreezeException{
    public ResourceNotFoundException(int code, String message) {
        super(code, message);
    }
}

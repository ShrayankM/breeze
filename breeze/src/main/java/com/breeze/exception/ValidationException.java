package com.breeze.exception;

public class ValidationException extends BreezeException {
    public ValidationException(int code, String message) {
        super(code, message);
    }
}

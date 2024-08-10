package com.breeze.exception;

import com.breeze.response.CommonResponse;

import java.util.Objects;

public class BreezeException extends Exception {

    private CommonResponse<Object> errorResponse;

    public BreezeException(int code, String message) {
        super(message);
        if (Objects.isNull(errorResponse)) {
            errorResponse = new CommonResponse<>();
        }
        errorResponse.setCode(code);
        errorResponse.setMessage(message);
    }

    public CommonResponse<Object> getErrorResponse() {
        return errorResponse;
    }
}

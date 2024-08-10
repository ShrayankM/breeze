package com.breeze.util;

import com.breeze.constant.BreezeErrorCodes;
import com.breeze.exception.BreezeException;
import com.breeze.response.CommonResponse;

public class CommonResponseGenerator {

    public static <T> CommonResponse<T> okResponse(T data) {
        return new CommonResponse<>(0, "OK", data);
    }

    public static CommonResponse<Object> genericErrorResponse(Exception exception) {
        String errorMessage = "Error occurred some validation thrown";
        if (exception != null) {
            errorMessage = exception.getMessage();
        }

        int errorCode = 400;
        if (exception instanceof BreezeException) {
            return ((BreezeException) exception).getErrorResponse();
        }

        return new CommonResponse<>(errorCode, errorMessage);
    }
}

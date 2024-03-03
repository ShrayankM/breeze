package com.breeze.util;

import com.breeze.response.CommonResponse;

public class CommonResponseGenerator {

    public static <T> CommonResponse<T> okResponse(T data) {
        return new CommonResponse<>(0, "OK", data);
    }
}

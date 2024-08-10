package com.breeze.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonResponse<T> {

    private int code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public CommonResponse() { super(); }
    public CommonResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

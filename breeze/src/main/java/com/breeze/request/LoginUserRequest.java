package com.breeze.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUserRequest {

    @NotBlank
    private String emailAddress;

    @NotBlank
    private String password;
}

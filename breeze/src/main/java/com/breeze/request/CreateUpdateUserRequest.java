package com.breeze.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUpdateUserRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String name;

    private String emailAddress;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;

    private Boolean isAdminUser;
}

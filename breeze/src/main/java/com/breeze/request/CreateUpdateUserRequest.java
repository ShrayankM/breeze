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
    private String emailAddress;

    @NotBlank
    private String userId;

    private Boolean isAdminUser;
}

package com.breeze.response;

import com.breeze.constant.BreezeConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private String code;

    private String userName;

    private String name;

    private String emailAddress;

    private String phoneNumber;

    private String password;

    private Boolean isEmailVerified;

    private Boolean isPhoneVerified;

    @Enumerated(EnumType.STRING)
    private BreezeConstants.UserType userType;
}

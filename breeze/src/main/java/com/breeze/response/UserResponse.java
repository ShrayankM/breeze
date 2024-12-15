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

    private Long readingBookCount;

    private Long completedBookCount;

    private Long wishlistedBookCount;

    private Long totalBooksInLibrary;

    @Enumerated(EnumType.STRING)
    private BreezeConstants.UserType userType;
}

package com.breeze.model;

import com.breeze.constant.BreezeConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "breeze_user")
@Getter
@Setter
public class BreezeUser extends AbstractModelWithCode  {

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;

    @Column(name = "is_phone_verified")
    private Boolean isPhoneVerified;

    @Column(name = "user_type", columnDefinition = "enum")
    @Enumerated(EnumType.STRING)
    private BreezeConstants.UserType userType;

}

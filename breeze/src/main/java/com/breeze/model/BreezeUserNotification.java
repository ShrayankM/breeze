package com.breeze.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "breeze_user_notification")
public class BreezeUserNotification extends AbstractModelWithCode {

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "data", columnDefinition = "JSON")
    private String data;

}

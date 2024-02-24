package com.breeze.model;

import com.breeze.constant.BreezeConstants;
import jakarta.persistence.*;

@Entity
@Table(name = "breeze_user_suggestion")
public class BreezeUserSuggestion extends AbstractModelWithCode {

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "user_suggestion_status", columnDefinition = "enum")
    @Enumerated(EnumType.STRING)
    private BreezeConstants.BreezeUserSuggestionStatus userSuggestionStatus;

    @Column(name = "suggestion")
    private String suggestion;
}

package com.breeze.model;

import com.breeze.constant.BreezeConstants.BreezeUserBookApprovalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "breeze_user_book_approval")
@Getter
@Setter
public class BreezeUserBookApproval extends AbstractModelWithCode {

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "data", columnDefinition = "JSON")
    private String data;

    @Column(name = "approval_status", columnDefinition = "enum")
    @Enumerated(EnumType.STRING)
    private BreezeUserBookApprovalStatus approvalStatus;

    @Column(name = "approved_at")
    private Date approvedAt;

    @Column(name = "rejected_at")
    private Date rejectedAt;

    @Column(name = "rejection_reason")
    private String rejectionReason;
}

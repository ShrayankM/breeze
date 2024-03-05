package com.breeze.request;

import com.breeze.constant.BreezeConstants.UserBookApprovalStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateBookApproval {

    @NotBlank
    private String code;

    @NotBlank
    private String userCode;

    @NotBlank
    private UserBookApprovalStatus approvalStatus;

    private String rejectionReason;
}

package com.breeze.request;

import com.breeze.constant.BreezeConstants.BreezeUserApprovalStatus;
import lombok.Data;

@Data
public class BookApprovalListRequest {

    private BreezeUserApprovalStatus approvalStatus;
}

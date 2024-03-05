package com.breeze.request;

import com.breeze.constant.BreezeConstants.BreezeUserBookApprovalStatus;
import lombok.Data;

@Data
public class FetchBookApprovalList {

    private BreezeUserBookApprovalStatus approvalStatus;
}

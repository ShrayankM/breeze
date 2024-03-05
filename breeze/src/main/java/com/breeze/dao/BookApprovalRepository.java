package com.breeze.dao;

import com.breeze.constant.BreezeConstants.UserBookApprovalStatus;
import com.breeze.model.BreezeUserBookApproval;

import java.util.List;

public interface BookApprovalRepository extends GenericDao {

    List<BreezeUserBookApproval> getListOfApprovalRequests(UserBookApprovalStatus status);

    BreezeUserBookApproval getApprovalRequestFromCode(String code);
}

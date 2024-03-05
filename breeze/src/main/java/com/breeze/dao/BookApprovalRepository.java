package com.breeze.dao;

import com.breeze.constant.BreezeConstants.BreezeUserBookApprovalStatus;
import com.breeze.model.BreezeUserBookApproval;

import java.util.List;

public interface BookApprovalRepository extends GenericDao {

    List<BreezeUserBookApproval> getListOfApprovalRequests(BreezeUserBookApprovalStatus status);
}

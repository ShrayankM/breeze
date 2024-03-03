package com.breeze.dao;

import com.breeze.constant.BreezeConstants.BreezeUserApprovalStatus;
import com.breeze.model.BreezeUserApproval;

import java.util.List;

public interface BookApprovalRepository extends GenericDao {

    List<BreezeUserApproval> getListOfApprovalRequests(BreezeUserApprovalStatus status);
}

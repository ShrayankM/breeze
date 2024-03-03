package com.breeze.service;

import com.breeze.request.BookApprovalListRequest;
import com.breeze.request.BookApprovalRequest;
import com.breeze.response.BookApprovalResponseList;
import org.springframework.stereotype.Service;

@Service
public interface BookApprovalService {

    void addBookApprovalRequest(BookApprovalRequest request);

    BookApprovalResponseList getBookApprovalRequestList(BookApprovalListRequest request);
}

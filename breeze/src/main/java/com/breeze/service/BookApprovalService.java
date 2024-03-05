package com.breeze.service;

import com.breeze.request.FetchBookApprovalList;
import com.breeze.request.CreateBookApproval;
import com.breeze.request.UpdateBookApproval;
import com.breeze.response.BookApprovalList;
import org.springframework.stereotype.Service;

@Service
public interface BookApprovalService {

    void createBookApprovalRequest(CreateBookApproval request);

    BookApprovalList fetchBookApprovalRequests(FetchBookApprovalList request);

    void updateBookApprovalRequest(UpdateBookApproval request);
}

package com.breeze.service;

import com.breeze.exception.BreezeException;
import com.breeze.model.BreezeUserBookApproval;
import com.breeze.request.FetchBookApprovalList;
import com.breeze.request.CreateBookApproval;
import com.breeze.request.UpdateBookApproval;
import com.breeze.response.BookApprovalList;
import org.springframework.stereotype.Service;

@Service
public interface BookApprovalService {

    void createBookApprovalRequest(CreateBookApproval request);

    BookApprovalList fetchBookApprovalRequestList(FetchBookApprovalList request);

    BreezeUserBookApproval updateBookApprovalRequest(UpdateBookApproval request) throws BreezeException;
}

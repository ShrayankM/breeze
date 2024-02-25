package com.breeze.service;

import com.breeze.request.BookApprovalRequest;
import org.springframework.stereotype.Service;

@Service
public interface BookApprovalService {

    void addBookApprovalRequest(BookApprovalRequest request);
}

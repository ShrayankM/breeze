package com.breeze.service.impl;

import com.breeze.model.BreezeUserApproval;
import com.breeze.request.BookApprovalRequest;
import com.breeze.service.BookApprovalService;
import com.breeze.util.RequestToModelConverter;
import org.springframework.stereotype.Service;


@Service
public class BookApprovalServiceImpl implements BookApprovalService {

    @Override
    public void addBookApprovalRequest(BookApprovalRequest request) {

        // * Validate incoming data has mandatory fields (name, author-name, isbn)

        // * Create record to add new incoming book approval request
        BreezeUserApproval model = RequestToModelConverter.createBookApprovalRequestToModel(request);

        // * Insert created record in DB
    }
}

package com.breeze.service.impl;

import com.breeze.dao.GenericDao;
import com.breeze.model.BreezeUserApproval;
import com.breeze.request.BookApprovalRequest;
import com.breeze.service.BookApprovalService;
import com.breeze.util.LoggerWrapper;
import com.breeze.util.RequestToModelConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookApprovalServiceImpl implements BookApprovalService {

    public static final LoggerWrapper logger = LoggerWrapper.getLogger(BookApprovalServiceImpl.class);

    @Autowired
    GenericDao genericDao;

    @Override
    @Transactional
    public void addBookApprovalRequest(BookApprovalRequest request) {
        logger.info("Book Approval Request Received for user = {}", request.getUserCode());

        // * Validate incoming data has mandatory fields (name, author-name, isbn)

        // * Create record to add new incoming book approval request
        BreezeUserApproval model = RequestToModelConverter.createBookApprovalRequestToModel(request);

        // * Insert created record in DB
        genericDao.create(model);
        logger.info("Book Approval Request Submitted successfully for user = {}", request.getUserCode());
    }
}

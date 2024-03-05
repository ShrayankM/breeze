package com.breeze.service.impl;

import com.breeze.dao.BookApprovalRepository;
import com.breeze.dao.GenericDao;
import com.breeze.model.BreezeUserBookApproval;
import com.breeze.request.FetchBookApprovalList;
import com.breeze.request.CreateBookApproval;
import com.breeze.response.BookApprovalList;
import com.breeze.service.BookApprovalService;
import com.breeze.util.LoggerWrapper;
import com.breeze.util.ModelToResponseConverter;
import com.breeze.util.RequestToModelConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookApprovalServiceImpl implements BookApprovalService {

    public static final LoggerWrapper logger = LoggerWrapper.getLogger(BookApprovalServiceImpl.class);

    @Autowired
    GenericDao genericDao;

    @Autowired
    BookApprovalRepository bookApprovalRepository;

    @Override
    @Transactional
    public void createBookApprovalRequest(CreateBookApproval request) {
        logger.info("Book Approval Request Received for user = {}", request.getUserCode());

        // * Validate incoming data has mandatory fields (name, author-name, isbn)

        // * Create record to add new incoming book approval request
        BreezeUserBookApproval model = RequestToModelConverter.createBookApprovalRequestToModel(request);

        // * Insert created record in DB
        genericDao.create(model);
        logger.info("Book Approval Request Submitted successfully for user = {}", request.getUserCode());
    }

    @Override
    public BookApprovalList fetchBookApprovalRequests(FetchBookApprovalList request) {
        logger.info("Fetching list of all {} approval requests", request);
        BookApprovalList bookApprovalResponseList = new BookApprovalList();

        // * Fetch all requests with state sent in request
        List<BreezeUserBookApproval> breezeUserApprovalRequestList = bookApprovalRepository.getListOfApprovalRequests(request.getApprovalStatus());

        // * Convert the data from DB to correct response
        bookApprovalResponseList = ModelToResponseConverter.getBookApprovalResponseFromModel(breezeUserApprovalRequestList);

        // * return the response
        return bookApprovalResponseList;
    }
}

package com.breeze.service.impl;

import com.breeze.constant.BreezeConstants.UserBookApprovalStatus;
import com.breeze.dao.BookApprovalRepository;
import com.breeze.dao.GenericDao;
import com.breeze.model.BreezeUserBookApproval;
import com.breeze.request.FetchBookApprovalList;
import com.breeze.request.CreateBookApproval;
import com.breeze.request.UpdateBookApproval;
import com.breeze.response.BookApprovalList;
import com.breeze.service.BookApprovalService;
import com.breeze.util.LoggerWrapper;
import com.breeze.util.MiscUtils;
import com.breeze.util.ModelToResponseConverter;
import com.breeze.util.RequestToModelConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

        BreezeUserBookApproval model = RequestToModelConverter.createBookApprovalRequestToModel(request);
        genericDao.create(model);

        logger.info("Book Approval Request Submitted successfully for user = {}", request.getUserCode());
    }

    @Override
    public BookApprovalList fetchBookApprovalRequests(FetchBookApprovalList request) {
        logger.info("Fetching list of all {} approval requests", request);

        BookApprovalList bookApprovalResponseList;
        List<BreezeUserBookApproval> breezeUserApprovalRequestList = bookApprovalRepository.getListOfApprovalRequests(request.getApprovalStatus());
        bookApprovalResponseList = ModelToResponseConverter.getBookApprovalResponseFromModel(breezeUserApprovalRequestList);

        return bookApprovalResponseList;
    }

    @Override
    @Transactional
    public void updateBookApprovalRequest(UpdateBookApproval request) {
        logger.info("Updating the book approval status for request = {}", request);

        BreezeUserBookApproval breezeUserBookApproval = bookApprovalRepository.getApprovalRequestFromCode(request.getCode());
        UserBookApprovalStatus oldStatus = breezeUserBookApproval.getApprovalStatus();
        UserBookApprovalStatus newStatus = request.getApprovalStatus();

        if (UserBookApprovalStatus.REJECTED.equals(newStatus) && MiscUtils.isStringNullOrEmpty(request.getRejectionReason())) {
            logger.error("No rejection reason present in request for status update REJECTED");
            return;
        }

        if (isNewStatusValid(oldStatus, newStatus)) {
            breezeUserBookApproval.setApprovalStatus(newStatus);

            if (UserBookApprovalStatus.APPROVED.equals(newStatus)) {
                breezeUserBookApproval.setApprovedAt(new Date());
            } else if (UserBookApprovalStatus.REJECTED.equals(newStatus)) {
                breezeUserBookApproval.setRejectedAt(new Date());
                breezeUserBookApproval.setRejectionReason(request.getRejectionReason());
            }
            bookApprovalRepository.update(breezeUserBookApproval);
            logger.info("Approval request status updated successfully");
        } else {
            logger.error("Approval request status cannot be updated from = {} to {}", oldStatus, newStatus);
        }
    }

    private boolean isNewStatusValid(UserBookApprovalStatus oldStatus, UserBookApprovalStatus newStatus) {
        if (oldStatus.ordinal() > newStatus.ordinal()) {
            return false;
        }

        switch (oldStatus) {
            case SUBMITTED -> {
                if (UserBookApprovalStatus.UNDER_REVIEW.equals(newStatus) || UserBookApprovalStatus.APPROVED.equals(newStatus) || UserBookApprovalStatus.REJECTED.equals(newStatus)) {
                    return true;
                }
            }
            case UNDER_REVIEW -> {
                if (UserBookApprovalStatus.APPROVED.equals(newStatus) || UserBookApprovalStatus.REJECTED.equals(newStatus)) {
                    return true;
                }
            }
        }
        return false;
    }
}

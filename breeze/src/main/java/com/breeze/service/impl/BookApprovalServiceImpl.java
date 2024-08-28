package com.breeze.service.impl;

import com.breeze.constant.BreezeConstants.UserBookApprovalStatus;
import com.breeze.constant.BreezeErrorCodes;
import com.breeze.dao.BookApprovalRepository;
import com.breeze.dao.GenericDao;
import com.breeze.exception.BreezeException;
import com.breeze.exception.ResourceNotFoundException;
import com.breeze.exception.ValidationException;
import com.breeze.model.BreezeBookDetails;
import com.breeze.model.BreezeUserBookApproval;
import com.breeze.request.FetchBookApprovalList;
import com.breeze.request.CreateBookApproval;
import com.breeze.request.UpdateBookApproval;
import com.breeze.response.BookApprovalList;
import com.breeze.service.BookApprovalService;
import com.breeze.util.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;


@Service
public class BookApprovalServiceImpl implements BookApprovalService {

    public static final LoggerWrapper logger = LoggerWrapper.getLogger(BookApprovalServiceImpl.class);

    @Autowired
    GenericDao genericDao;

    @Autowired
    BookApprovalRepository bookApprovalRepository;

    @Autowired
    RequestValidator validator;

    @Override
    @Transactional
    public void createBookApprovalRequest(CreateBookApproval request) {
        logger.info("Book Approval Request Received for user = {}", request.getUserCode());

        // TODO add validations before creating book request
        validator.validate(request);

        BreezeUserBookApproval model = RequestToModelConverter.createBookApprovalRequestToModel(request);
        genericDao.create(model);

        logger.info("Book Approval Request Submitted successfully for user = {}", request.getUserCode());
    }

    @Override
    public BookApprovalList fetchBookApprovalRequestList(FetchBookApprovalList request) {
        logger.info("Fetching list of all {} approval requests", request);

        BookApprovalList bookApprovalResponseList;
        List<BreezeUserBookApproval> breezeUserApprovalRequestList = bookApprovalRepository.getListOfApprovalRequests(request.getApprovalStatus());

        if (CollectionUtils.isEmpty(breezeUserApprovalRequestList)) {
            logger.info("No request found for approval status = {}", request.getApprovalStatus());
            bookApprovalResponseList = new BookApprovalList();
            bookApprovalResponseList.setBookApprovalDataList(new ArrayList<>());
            bookApprovalResponseList.setCount(0L);
            return bookApprovalResponseList;
        }

        bookApprovalResponseList = ModelToResponseConverter.getBookApprovalResponseFromModel(breezeUserApprovalRequestList);
        return bookApprovalResponseList;
    }

    @Override
    @Transactional
    public BreezeUserBookApproval updateBookApprovalRequest(UpdateBookApproval request) throws BreezeException {
        logger.info("Updating the book approval status for request = {}", request);

        BreezeUserBookApproval breezeUserBookApproval = bookApprovalRepository.getApprovalRequestFromCode(request.getCode());
        if (Objects.isNull(breezeUserBookApproval)) {
            logger.error("No approval request found for code = {}", request.getCode());
            throw new ResourceNotFoundException(BreezeErrorCodes.DATA_NOT_FOUND,
                    BreezeErrorCodes.DATA_NOT_FOUND_MSG);
        }

        UserBookApprovalStatus oldStatus = breezeUserBookApproval.getApprovalStatus();
        UserBookApprovalStatus newStatus = request.getApprovalStatus();

        if (UserBookApprovalStatus.REJECTED.equals(newStatus) && !StringUtils.hasText(request.getRejectionReason())) {
            logger.error("No rejection cannot be null or empty to reject request");
            throw new ValidationException(BreezeErrorCodes.REJECTION_REASON_CANNOT_BE_NULL_OR_EMPTY_CODE,
                    BreezeErrorCodes.REJECTION_REASON_CANNOT_BE_NULL_OR_EMPTY_MSG);
        }

        if (isNewStatusValid(oldStatus, newStatus)) {
            breezeUserBookApproval.setApprovalStatus(newStatus);

            if (UserBookApprovalStatus.APPROVED.equals(newStatus)) {
                breezeUserBookApproval.setApprovedAt(new Date());

                // TODO create new book record as request is approved
                BreezeBookDetails breezeBookDetails = RequestToModelConverter.getBookDetailsFromApprovalRequest(breezeUserBookApproval);
                if (Objects.isNull(breezeBookDetails)) {
                    logger.error("Breeze Book details is null or empty");
                    throw new ResourceNotFoundException(BreezeErrorCodes.INVALID_BOOK_DETAILS_OBJECT_CODE,
                            BreezeErrorCodes.INVALID_BOOK_DETAILS_OBJECT_MSG);
                }

                // create new book details record after approval, update book approval request
                bookApprovalRepository.update(breezeUserBookApproval);
                genericDao.create(breezeBookDetails);
            } else if (UserBookApprovalStatus.REJECTED.equals(newStatus)) {
                breezeUserBookApproval.setRejectedAt(new Date());
                breezeUserBookApproval.setRejectionReason(request.getRejectionReason());
                bookApprovalRepository.update(breezeUserBookApproval);
            }
            logger.info("Approval request status updated successfully");
            return breezeUserBookApproval;
        } else {
            logger.error("Approval request status cannot be updated from = {} to {}", oldStatus, newStatus);
            throw new ResourceNotFoundException(BreezeErrorCodes.DATA_NOT_FOUND,BreezeErrorCodes.DATA_NOT_FOUND_MSG);
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

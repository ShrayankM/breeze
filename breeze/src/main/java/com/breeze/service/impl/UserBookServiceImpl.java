package com.breeze.service.impl;

import com.breeze.constant.BreezeConstants;
import com.breeze.constant.BreezeErrorCodes;
import com.breeze.dao.BookRepository;
import com.breeze.dao.GenericDao;
import com.breeze.dao.UserRepository;
import com.breeze.exception.BreezeException;
import com.breeze.exception.ResourceNotFoundException;
import com.breeze.exception.ValidationException;
import com.breeze.model.BreezeBookDetails;
import com.breeze.model.BreezeUser;
import com.breeze.model.BreezeUserBook;
import com.breeze.request.CreateUpdateUserBookRequest;
import com.breeze.response.BookDetailsResponse;
import com.breeze.response.UserBookResponse;
import com.breeze.service.BookService;
import com.breeze.service.UserBookService;
import com.breeze.util.LoggerWrapper;
import com.breeze.util.RequestToModelConverter;
import com.breeze.util.ModelToResponseConverter;
import com.breeze.util.ModelToResponseConverter;
import com.breeze.util.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserBookServiceImpl implements UserBookService {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(UserBookServiceImpl.class);

    @Autowired
    RequestValidator requestValidator;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenericDao genericDao;

    @Autowired
    BookService bookService;

    @Autowired
    UserRepository userRepository;


    @Override
    public UserBookResponse addBookForUser(CreateUpdateUserBookRequest request) throws BreezeException {
        UserBookResponse response = new UserBookResponse();

        requestValidator.validate(request);

        // check if record exists for this user for this book
        BreezeUserBook breezeUserBook = bookRepository.getUserBookFromCode(request.getUserCode(), request.getBookCode());
        if (Objects.isNull(breezeUserBook)) {
            // add new record to DB
            validateNewUserBookRequest(request);
            breezeUserBook = RequestToModelConverter.getUserBookFromRequest(request);
            response.setMessage("Book Added to User Library Successfully");
            genericDao.create(breezeUserBook);
        } else {
            response.setMessage("Book Already Exists in User Library");
        }

        response = ModelToResponseConverter.getUserBookFromModel(breezeUserBook);
        BookDetailsResponse bookDetailsResponse = bookService.getBookDetails(breezeUserBook.getBookCode());
        response.setBookDetailsResponse(bookDetailsResponse);
        return response;
    }

    @Override
    public UserBookResponse updateBookForUser(CreateUpdateUserBookRequest request) throws BreezeException {
        UserBookResponse response;
        requestValidator.validate(request);

        // check if record exists for this user for this book
        BreezeUserBook breezeUserBook = bookRepository.getUserBookFromCode(request.getUserCode(), request.getBookCode());
        if (!Objects.isNull(breezeUserBook)) {
            if (request.getBookStatus() != null) {
                // logic to set completed count for user
                if (BreezeConstants.BookStatus.COMPLETED.equals(request.getBookStatus())) {
                    Long currentCompletedCount = breezeUserBook.getCompletedCount();
                    breezeUserBook.setCompletedCount(currentCompletedCount + 1);
                }
                breezeUserBook.setBookStatus(request.getBookStatus());
            }

            if (request.getCurrentPage() != null) {
                breezeUserBook.setCurrentPage(request.getCurrentPage());
            }

            if (request.getUserRating() != null) {
                breezeUserBook.setUserRating(request.getUserRating());
            }

            if (request.getWishlist() != null) {
                breezeUserBook.setWishlist(request.getWishlist());
            }

            if (request.getIsDeleted() != null) {
                breezeUserBook.setIsDeleted(request.getIsDeleted());
            }
            genericDao.update(breezeUserBook);
        } else {
            throw new ValidationException(BreezeErrorCodes.DATA_NOT_FOUND, BreezeErrorCodes.DATA_NOT_FOUND_MSG);
        }

        response = ModelToResponseConverter.getUserBookFromModel(breezeUserBook);
        BookDetailsResponse bookDetailsResponse = bookService.getBookDetails(breezeUserBook.getBookCode());
        response.setBookDetailsResponse(bookDetailsResponse);
        return response;
    }

    private void validateNewUserBookRequest(CreateUpdateUserBookRequest request) throws BreezeException {

        // validate if book exists in DB
        BreezeBookDetails bookDetails = bookRepository.getBookDetailsUsingCode(request.getBookCode());
        if (Objects.isNull(bookDetails)) {
            logger.info("Book not found with code = {} not found in DB" + request.getBookCode());
            throw new ResourceNotFoundException(BreezeErrorCodes.DATA_NOT_FOUND,
                    BreezeErrorCodes.DATA_NOT_FOUND_MSG);
        }

        // validate is user exists for code
        BreezeUser breezeUser = userRepository.getUserByCode(request.getUserCode());
        if (Objects.isNull(breezeUser)) {
            logger.info("User with code = {} not found in DB: " + request.getUserCode());
            throw new ResourceNotFoundException(BreezeErrorCodes.DATA_NOT_FOUND,
                    BreezeErrorCodes.DATA_NOT_FOUND_MSG);
        }
    }
}

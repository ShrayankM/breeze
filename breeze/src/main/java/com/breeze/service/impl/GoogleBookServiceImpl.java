package com.breeze.service.impl;

import com.breeze.constant.BreezeErrorCodes;
import com.breeze.dao.BookRepository;
import com.breeze.dao.GenericDao;
import com.breeze.exception.BreezeException;
import com.breeze.exception.ValidationException;
import com.breeze.model.BreezeBookDetails;
import com.breeze.request.CreateBookRecords;
import com.breeze.response.BookDataResponse;
import com.breeze.response.GetListResponse;
import com.breeze.response.GoogleBookResponse;
import com.breeze.service.GoogleBookService;
import com.breeze.service.GoogleRestApiService;
import com.breeze.util.LoggerWrapper;
import com.breeze.util.ModelToResponseConverter;
import com.breeze.util.RequestToModelConverter;
import com.breeze.util.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GoogleBookServiceImpl implements GoogleBookService {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(GoogleRestApiServiceImpl.class);

    @Autowired
    GoogleRestApiService googleRestApiService;

    @Autowired
    RequestValidator requestValidator;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenericDao genericDao;

    @Override
    public GoogleBookResponse getBookUsingIsbn(String isbn) throws BreezeException {
        return googleRestApiService.getBookDataUsingIsbn(isbn);
    }

    @Override
    public GetListResponse<BookDataResponse> createBookRecords(CreateBookRecords request) throws BreezeException {

        requestValidator.validate(request);
        GetListResponse<BookDataResponse> bookDataResponseList = new GetListResponse<>();

        if (Objects.isNull(request)) {
            logger.error("Request to create-book records is null");
            throw new ValidationException(BreezeErrorCodes.INVALID_REQUEST_TO_CREATE_BOOKS_CODE,
                    BreezeErrorCodes.INVALID_REQUEST_TO_CREATE_BOOKS_MSG);
        }

        if (CollectionUtils.isEmpty(request.getIsbnList())) {
            logger.error("Isbn-list in request to create-book records is empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_ISBN_LIST_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_ISBN_LIST_IN_REQUEST_MSG);
        }

        // fetch existing records belong to isbn sent in request
        List<BreezeBookDetails> breezeBookDetailsList = bookRepository.getListOfBooksUsingIsbn(
                request.getIsbnList(), request.getIsIsbn10());

        // if records exists for isbn in request filter them out
        if (!CollectionUtils.isEmpty(breezeBookDetailsList)) {
            List<String> isbnList = new ArrayList<>();
            if (Boolean.TRUE.equals(request.getIsIsbn10())) {
                isbnList = breezeBookDetailsList.stream().map(BreezeBookDetails::getIsbn10).toList();
            } else {
                isbnList = breezeBookDetailsList.stream().map(BreezeBookDetails::getIsbn13).toList();
            }
            request.getIsbnList().removeAll(isbnList);
        }

        List<BreezeBookDetails> createList = new ArrayList<>();
        for (String isbn: request.getIsbnList()) {
            GoogleBookResponse googleBookResponse = googleRestApiService.getBookDataUsingIsbn(isbn);

            if (Objects.isNull(googleBookResponse)) {
                logger.error("Google book response is null for isbn = {}", isbn);
                continue;
            }

            BreezeBookDetails breezeBookDetails = RequestToModelConverter.getBookDetailsFromGoogleBookResponse(googleBookResponse);
            createList.add(breezeBookDetails);
        }

        genericDao.createAll(createList);
        List<BookDataResponse> responseList = ModelToResponseConverter.getBookListResponseFromModel(createList);
        bookDataResponseList.setList(responseList);
        bookDataResponseList.setTotalCount(responseList.size());
        return bookDataResponseList;
    }
}

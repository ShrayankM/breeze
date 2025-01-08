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
import com.breeze.request.FetchBookList;
import com.breeze.request.UpdateBookRating;
import com.breeze.response.BookDataResponse;
import com.breeze.response.BookDetailsResponse;
import com.breeze.response.GetListResponse;
import com.breeze.service.BookService;
import com.breeze.util.LoggerWrapper;
import com.breeze.util.ModelToResponseConverter;
import com.breeze.util.RequestValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(BookServiceImpl.class);

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenericDao genericDao;

    @Autowired
    RequestValidator requestValidator;

    @Autowired
    UserRepository userRepository;

    @Override
    public GetListResponse<BookDataResponse> getBooks(FetchBookList request) {
        GetListResponse<BookDataResponse> bookListResponse = new GetListResponse<>();

        // validate incoming request
        requestValidator.validate(request);

        List<BreezeBookDetails> breezeBookDetailsList = bookRepository.getListOfBooks(request);
        if (CollectionUtils.isEmpty(breezeBookDetailsList)) {
            bookListResponse.setList(new ArrayList<>());
            bookListResponse.setTotalCount(0);
            return bookListResponse;
        }

        List<BreezeUserBook> userBookList = bookRepository.getListOfBooksForUser(request.getUserCode(), null);
        Map<String, BreezeUserBook> userBookMap = userBookList.stream().collect(Collectors.toMap(BreezeUserBook::getBookCode, x -> x));

        List<BookDataResponse> responseList = ModelToResponseConverter.getBookListResponseFromModel(breezeBookDetailsList);
        for (BookDataResponse bookDataResponse : responseList) {
            BreezeUserBook breezeUserBook = userBookMap.get(bookDataResponse.getCode());
            if (Objects.nonNull(breezeUserBook)) {
                // set isAddedToLibrary
                bookDataResponse.setIsAddedToLibrary(BreezeConstants.BookStatus.LIBRARY.equals(breezeUserBook.getBookStatus())
                        || BreezeConstants.BookStatus.READING.equals(breezeUserBook.getBookStatus())
                        || BreezeConstants.BookStatus.COMPLETED.equals(breezeUserBook.getBookStatus()));

                // set isAddedToWishlist
                bookDataResponse.setIsAddedToWishlist(BreezeConstants.BookStatus.WISHLIST.equals(breezeUserBook.getBookStatus()));
            }
        }
        bookListResponse.setList(responseList);
        bookListResponse.setTotalCount(responseList.size());
        return bookListResponse;
    }

    @Override
    public GetListResponse<BookDataResponse> getBooksForUser(FetchBookList request) throws BreezeException {
        GetListResponse<BookDataResponse> bookDataResponseList = new GetListResponse<>();

        // validate the userCode in request is not null or empty
        if (Objects.isNull(request.getUserCode()) || !StringUtils.hasText(request.getUserCode())) {
            logger.error("User code in request is null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_USER_CODE,
                    BreezeErrorCodes.INVALID_USER_CODE_MSG);
        }

        // fetch books for particular user using userCode & bookStatusList
        List<BreezeUserBook> breezeUserBookList = bookRepository.getListOfBooksForUser(request.getUserCode(), request.getBookStatusList());
        Map<String, BreezeUserBook> userBookMap = breezeUserBookList.stream().collect(Collectors.toMap(BreezeUserBook::getBookCode, x -> x));

        if (CollectionUtils.isEmpty(breezeUserBookList)) {
            logger.info("No books found for user: " + request.getUserCode());
            bookDataResponseList.setList(new ArrayList<>());
            bookDataResponseList.setTotalCount(0);
            return bookDataResponseList;
        }

        List<String> bookCodeList = breezeUserBookList.stream().map(BreezeUserBook::getBookCode).toList();
        List<BreezeBookDetails> breezeBookDetailsList = bookRepository.getListOfBooksUsingCodeList(bookCodeList);
        if (CollectionUtils.isEmpty(breezeBookDetailsList)) {
            logger.info("No books details found for books for user: " + request.getUserCode());
            bookDataResponseList.setList(new ArrayList<>());
            bookDataResponseList.setTotalCount(0);
            return bookDataResponseList;
        }

        // * adding status back to book details response
        List<BookDataResponse> responseList = ModelToResponseConverter.getBookListResponseFromModel(breezeBookDetailsList);
        for (BookDataResponse bookDataResponse : responseList) {
            BreezeUserBook userBook = userBookMap.get(bookDataResponse.getCode());
            if (Objects.nonNull(userBook)) {
                bookDataResponse.setBookStatus(userBook.getBookStatus());
            }
        }

        bookDataResponseList.setList(responseList);
        bookDataResponseList.setTotalCount(responseList.size());
        return bookDataResponseList;
    }

    @Override
    public GetListResponse<BookDataResponse> searchBooksByNameAndAuthor(String searchQuery) throws BreezeException {
        GetListResponse<BookDataResponse> bookDataListResponse = new GetListResponse<>();

        if (Objects.isNull(searchQuery) || !StringUtils.hasText(searchQuery)) {
            logger.error("Search Query in request cannot null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_SEARCH_QUERY_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_SEARCH_QUERY_IN_REQUEST_MSG);
        }

        List<BreezeBookDetails> bookDetailsList = bookRepository.getBooksByNameAndAuthor(searchQuery);
        if (CollectionUtils.isEmpty(bookDetailsList)) {
            logger.info("No books with name or author: " + searchQuery  + " found in the DB");
            bookDataListResponse.setList(new ArrayList<>());
            bookDataListResponse.setTotalCount(0);
            return bookDataListResponse;
        }

        List<BookDataResponse> responseList = ModelToResponseConverter.getBookListResponseFromModel(bookDetailsList);
        bookDataListResponse.setList(responseList);
        bookDataListResponse.setTotalCount(responseList.size());
        return bookDataListResponse;
    }

    @Override
    public BookDetailsResponse getBookDetails(String bookCode) throws BreezeException {
        BookDetailsResponse bookDetailsResponse;

        if (Objects.isNull(bookCode) || !StringUtils.hasText(bookCode)) {
            logger.error("Book code in request cannot null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_BOOK_CODE_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_BOOK_CODE_IN_REQUEST_CODE_MSG);
        }

        BreezeBookDetails bookDetails = bookRepository.getBookDetailsUsingCode(bookCode);
        if (Objects.isNull(bookDetails)) {
            logger.error("Book details not found for book code: " + bookCode);
            throw new ResourceNotFoundException(BreezeErrorCodes.DATA_NOT_FOUND,
                    BreezeErrorCodes.DATA_NOT_FOUND_MSG);
        }
        bookDetailsResponse = ModelToResponseConverter.getBookDetailsResponseFromModel(bookDetails);
        return bookDetailsResponse;
    }

    @Override
    public BookDetailsResponse getBookDetailsForUser(String bookCode, String userCode) throws BreezeException {
        BookDetailsResponse bookDetailsResponse;

        if (Objects.isNull(bookCode) || !StringUtils.hasText(bookCode)) {
            logger.error("Book code in request cannot null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_BOOK_CODE_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_BOOK_CODE_IN_REQUEST_CODE_MSG);
        }

        if (Objects.isNull(userCode) || !StringUtils.hasText(userCode)) {
            logger.error("User code in request is null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_USER_CODE,
                    BreezeErrorCodes.INVALID_USER_CODE_MSG);
        }

        BreezeUser breezeUser = userRepository.getUserByCode(userCode);
        if (breezeUser == null) {
            logger.error("User not found with code {}", userCode);
            throw new ResourceNotFoundException(BreezeErrorCodes.USER_NOT_FOUND_ERROR_CODE,
                    BreezeErrorCodes.USER_NOT_FOUND_ERROR_MSG);
        }

        BreezeUserBook breezeUserBook = bookRepository.getUserBookFromCode(userCode, bookCode);
        if (Objects.isNull(breezeUserBook)) {
            logger.error("No record found in DB for user: " + userCode + " and book: " + bookCode);
            throw new ResourceNotFoundException(BreezeErrorCodes.DATA_NOT_FOUND,
                    BreezeErrorCodes.DATA_NOT_FOUND_MSG);
        }

        BreezeBookDetails bookDetails = bookRepository.getBookDetailsUsingCode(bookCode);
        if (Objects.isNull(bookDetails)) {
            logger.error("Book details not found for book code: " + bookCode);
            throw new ResourceNotFoundException(BreezeErrorCodes.DATA_NOT_FOUND,
                    BreezeErrorCodes.DATA_NOT_FOUND_MSG);
        }

        bookDetailsResponse = ModelToResponseConverter.getBookDetailsResponseFromModel(bookDetails);
        bookDetailsResponse.setBookStatus(breezeUserBook.getBookStatus());
        bookDetailsResponse.setUserRating(BigDecimal.valueOf(breezeUserBook.getUserRating()));
        return bookDetailsResponse;
    }

    @Override
    public GetListResponse<BookDataResponse> searchBooksByNameAndAuthorForUser(String searchQuery, String userCode) throws BreezeException {

        GetListResponse<BookDataResponse> bookDataResponseList = new GetListResponse<>();

        if (!StringUtils.hasText(searchQuery)) {
            logger.error("Search Query in request cannot null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_SEARCH_QUERY_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_SEARCH_QUERY_IN_REQUEST_MSG);
        }

        if (!StringUtils.hasText(userCode)) {
            logger.error("User code in request cannot null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_USER_CODE_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_USER_CODE_IN_REQUEST_MSG);
        }

        List<BreezeConstants.BookStatus> bookStatusList = Arrays.asList(BreezeConstants.BookStatus.READING, BreezeConstants.BookStatus.COMPLETED,
                BreezeConstants.BookStatus.LIBRARY);

        List<BreezeUserBook> breezeUserBookList = bookRepository.getListOfBookForUserUsingCode(userCode, bookStatusList);
        Map<String, BreezeUserBook> userBookMap = breezeUserBookList.stream().collect(Collectors.toMap(BreezeUserBook::getBookCode, Function.identity()));
        if (CollectionUtils.isEmpty(breezeUserBookList)) {
            logger.info("No books found for user with code: " + userCode);
            bookDataResponseList.setList(new ArrayList<>());
            bookDataResponseList.setTotalCount(0);
            return bookDataResponseList;
        }

        List<String> bookCodeList = breezeUserBookList.stream().map(BreezeUserBook::getBookCode).toList();

        List<BreezeBookDetails> breezeBookDetailsList = bookRepository.getListOfBooksUsingCodeAndNameOrAuthor(bookCodeList, searchQuery);
        if (CollectionUtils.isEmpty(breezeBookDetailsList)) {
            logger.info("No books details found for books for user: " + userCode);
            bookDataResponseList.setList(new ArrayList<>());
            bookDataResponseList.setTotalCount(0);
            return bookDataResponseList;
        }

        List<BookDataResponse> responseList = ModelToResponseConverter.getBookListResponseFromModel(breezeBookDetailsList);
        for (BookDataResponse bookDataResponse : responseList) {
            BreezeUserBook breezeUserBook = userBookMap.get(bookDataResponse.getCode());
            bookDataResponse.setBookStatus(breezeUserBook.getBookStatus());
        }
        bookDataResponseList.setList(responseList);
        bookDataResponseList.setTotalCount(responseList.size());
        return bookDataResponseList;
    }

    @Override
    public GetListResponse<BookDataResponse> searchWishlistedBooksByNameAndAuthorForUser(String searchQuery, String userCode) throws BreezeException {

        GetListResponse<BookDataResponse> bookDataResponseList = new GetListResponse<>();

        if (!StringUtils.hasText(searchQuery)) {
            logger.error("Search Query in request cannot null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_SEARCH_QUERY_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_SEARCH_QUERY_IN_REQUEST_MSG);
        }

        if (!StringUtils.hasText(userCode)) {
            logger.error("User code in request cannot null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_USER_CODE_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_USER_CODE_IN_REQUEST_MSG);
        }

        List<BreezeConstants.BookStatus> bookStatusList = List.of(BreezeConstants.BookStatus.WISHLIST);

        List<BreezeUserBook> breezeUserBookList = bookRepository.getListOfBookForUserUsingCode(userCode, bookStatusList);
        Map<String, BreezeUserBook> userBookMap = breezeUserBookList.stream().collect(Collectors.toMap(BreezeUserBook::getBookCode, Function.identity()));
        if (CollectionUtils.isEmpty(breezeUserBookList)) {
            logger.info("No books found for user with code: " + userCode);
            bookDataResponseList.setList(new ArrayList<>());
            bookDataResponseList.setTotalCount(0);
            return bookDataResponseList;
        }

        List<String> bookCodeList = breezeUserBookList.stream().map(BreezeUserBook::getBookCode).toList();

        List<BreezeBookDetails> breezeBookDetailsList = bookRepository.getListOfBooksUsingCodeAndNameOrAuthor(bookCodeList, searchQuery);
        if (CollectionUtils.isEmpty(breezeBookDetailsList)) {
            logger.info("No books details found for books for user: " + userCode);
            bookDataResponseList.setList(new ArrayList<>());
            bookDataResponseList.setTotalCount(0);
            return bookDataResponseList;
        }

        List<BookDataResponse> responseList = ModelToResponseConverter.getBookListResponseFromModel(breezeBookDetailsList);
        for (BookDataResponse bookDataResponse : responseList) {
            BreezeUserBook breezeUserBook = userBookMap.get(bookDataResponse.getCode());
            bookDataResponse.setBookStatus(breezeUserBook.getBookStatus());
        }
        bookDataResponseList.setList(responseList);
        bookDataResponseList.setTotalCount(responseList.size());
        return bookDataResponseList;
    }

    @Override
    @Transactional
    public void updateBookRatingForUser(UpdateBookRating request) throws BreezeException {

        if (!StringUtils.hasText(request.getUserCode())) {
            logger.error("User code in request cannot be null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_USER_CODE,
                    BreezeErrorCodes.INVALID_USER_CODE_MSG);
        }

        if (!StringUtils.hasText(request.getBookCode())) {
            logger.error("Book code in request cannot be null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_BOOK_CODE_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_BOOK_CODE_IN_REQUEST_CODE_MSG);
        }

        if (Objects.isNull(request.getRating()) || !StringUtils.hasText(request.getRating().toString())) {
            logger.error("Rating sent in request cannot be null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_RATING_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_RATING_IN_REQUEST_MSG);
        }


        BreezeUserBook breezeUserBook = bookRepository.getUserBookFromCode(request.getUserCode(), request.getBookCode());
        if (Objects.isNull(breezeUserBook)) {
            logger.error("No record found in DB for user: " + request.getUserCode() + " and book: " + request.getBookCode());
            throw new ResourceNotFoundException(BreezeErrorCodes.DATA_NOT_FOUND,
                    BreezeErrorCodes.DATA_NOT_FOUND_MSG);
        }

        breezeUserBook.setUserRating(request.getRating());
        genericDao.update(breezeUserBook);

        BreezeBookDetails breezeBookDetails = bookRepository.getBookDetailsUsingCode(request.getBookCode());
        if (Objects.isNull(breezeBookDetails)) {
            logger.error("No record found in DB for book: " + request.getBookCode());
            throw new ResourceNotFoundException(BreezeErrorCodes.DATA_NOT_FOUND,
                    BreezeErrorCodes.DATA_NOT_FOUND_MSG);
        }

        List<BreezeUserBook> userBooksList = bookRepository.getListOfUserBooksUsingBookCode(request.getBookCode());
        if (CollectionUtils.isEmpty(userBooksList)) {
            logger.info("No user book records found for book code : " + request.getBookCode());
            throw new ResourceNotFoundException(BreezeErrorCodes.DATA_NOT_FOUND,
                    BreezeErrorCodes.DATA_NOT_FOUND_MSG);
        }

        userBooksList = userBooksList.stream().filter(ub -> ub.getUserRating() > 0).toList();

        long currentReviewCount = userBooksList.size();
        long currentRatingSum = userBooksList.stream().mapToLong(BreezeUserBook::getUserRating).sum();
        BigDecimal updatedRating = BigDecimal.valueOf(currentRatingSum).divide(BigDecimal.valueOf(currentReviewCount), 2, RoundingMode.HALF_UP);

        breezeBookDetails.setUserRating(updatedRating);
        breezeBookDetails.setReviewCount(currentReviewCount + 1);
        genericDao.update(breezeBookDetails);
    }
}

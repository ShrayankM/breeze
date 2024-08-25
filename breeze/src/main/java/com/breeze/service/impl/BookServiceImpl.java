package com.breeze.service.impl;

import com.breeze.constant.BreezeConstants;
import com.breeze.constant.BreezeConstants.BookGenre;
import com.breeze.constant.BreezeErrorCodes;
import com.breeze.dao.BookRepository;
import com.breeze.dao.GenericDao;
import com.breeze.exception.BreezeException;
import com.breeze.exception.ResourceNotFoundException;
import com.breeze.exception.ValidationException;
import com.breeze.model.BreezeBookDetails;
import com.breeze.model.BreezeUserBook;
import com.breeze.request.FetchBookList;
import com.breeze.request.FetchBookList.YearOfPublishing;
import com.breeze.request.FetchBookList.NoOfPages;
import com.breeze.request.UpdateBookRating;
import com.breeze.response.BookDetailsResponse;
import com.breeze.response.BookListResponse;
import com.breeze.service.BookService;
import com.breeze.util.LoggerWrapper;
import com.breeze.util.MiscUtils;
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

@Service
public class BookServiceImpl implements BookService {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(BookServiceImpl.class);

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenericDao genericDao;

    @Autowired
    RequestValidator requestValidator;

    @Override
    public BookListResponse getBooks(FetchBookList request) {
        BookListResponse bookListResponse = new BookListResponse();

        // validate incoming request
        requestValidator.validate(request);

        setBookListFilters(request);

        List<BreezeBookDetails> breezeBookDetailsList = bookRepository.getListOfBooks(request.getGenreList(), request.getPages().getMinPages(),
                request.getPages().getMaxPages(), request.getYob().getStartDate(), request.getYob().getEndDate());

        if (CollectionUtils.isEmpty(breezeBookDetailsList)) {
            bookListResponse.setBookDetailsList(new ArrayList<>());
            bookListResponse.setCount(0);
            return bookListResponse;
        }

        bookListResponse = ModelToResponseConverter.getBookListResponseFromModel(breezeBookDetailsList);
        return bookListResponse;
    }

    @Override
    public BookListResponse getBooksForUser(FetchBookList request) throws BreezeException {
        BookListResponse bookListResponse = new BookListResponse();

        if (Objects.isNull(request.getUserCode()) || !StringUtils.hasText(request.getUserCode())) {
            logger.error("User code in request is null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_USER_CODE,
                    BreezeErrorCodes.INVALID_USER_CODE_MSG);
        }

        if (Objects.isNull(request.getBookStatus())) {
            logger.error("Book status in request is null");
            throw new ValidationException(BreezeErrorCodes.INVALID_BOOK_STATUS_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_BOOK_STATUS_IN_REQUEST_CODE_MSG);
        }

        setBookListFilters(request);

        List<BreezeUserBook> breezeUserBookList = bookRepository.getListOfBooksForUser(request.getUserCode(), request.getBookStatus());

        if (CollectionUtils.isEmpty(breezeUserBookList)) {
            logger.info("No books found for user: " + request.getUserCode());
            bookListResponse.setBookDetailsList(new ArrayList<>());
            bookListResponse.setCount(0);
            return bookListResponse;
        }
        List<String> bookCodeList = breezeUserBookList.stream().map(BreezeUserBook::getCode).toList();

        List<BreezeBookDetails> breezeBookDetailsList = bookRepository.getListOfBooksUsingCodeList(bookCodeList, request.getGenreList(), request.getPages().getMinPages(),
                request.getPages().getMaxPages(), request.getYob().getStartDate(), request.getYob().getEndDate());

        if (CollectionUtils.isEmpty(breezeBookDetailsList)) {
            logger.info("No books details found for books for user: " + request.getUserCode());
            bookListResponse.setBookDetailsList(new ArrayList<>());
            bookListResponse.setCount(0);
            return bookListResponse;
        }
        bookListResponse = ModelToResponseConverter.getBookListResponseFromModel(breezeBookDetailsList);
        return bookListResponse;
    }

    @Override
    public BookDetailsResponse getBookDetails(String bookCode) throws BreezeException {
        BookDetailsResponse bookDetailsResponse = new BookDetailsResponse();

        if (!StringUtils.hasText(bookCode)) {
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
    public BookListResponse getBooksByName(String bookName) throws BreezeException {
        BookListResponse bookListResponse = new BookListResponse();

        if (!StringUtils.hasText(bookName)) {
            logger.error("Book name in request cannot null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_BOOK_NAME_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_BOOK_NAME_IN_REQUEST_MSG);
        }

        List<BreezeBookDetails> bookDetailsList = bookRepository.getBooksByName(bookName);

        if (CollectionUtils.isEmpty(bookDetailsList)) {
            logger.info("No books with name: " + bookName + " found in the DB");
            bookListResponse.setBookDetailsList(new ArrayList<>());
            bookListResponse.setCount(0);
            return bookListResponse;
        }

        bookListResponse = ModelToResponseConverter.getBookListResponseFromModel(bookDetailsList);
        return bookListResponse;
    }

    @Override
    public BookListResponse getBooksByAuthor(String authorName) throws BreezeException {
        BookListResponse bookListResponse = new BookListResponse();

        if (!StringUtils.hasText(authorName)) {
            logger.error("Author Name in request cannot null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_AUTHOR_NAME_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_AUTHOR_NAME_IN_REQUEST_MSG);
        }

        List<BreezeBookDetails> bookDetailsList = bookRepository.getBooksByAuthor(authorName);

        if (CollectionUtils.isEmpty(bookDetailsList)) {
            logger.info("No books with author name: " + authorName + " found in the DB");
            bookListResponse.setBookDetailsList(new ArrayList<>());
            bookListResponse.setCount(0);
            return bookListResponse;
        }

        bookListResponse = ModelToResponseConverter.getBookListResponseFromModel(bookDetailsList);
        return bookListResponse;
    }

    @Override
    public BookListResponse getBooksByNameForUser(String bookName, String userCode) throws BreezeException {
        BookListResponse bookListResponse = new BookListResponse();

        if (!StringUtils.hasText(bookName)) {
            logger.error("Book name in request cannot null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_BOOK_NAME_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_BOOK_NAME_IN_REQUEST_MSG);
        }

        if (!StringUtils.hasText(userCode)) {
            logger.error("User code in request cannot null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_USER_CODE_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_USER_CODE_IN_REQUEST_MSG);
        }

        List<BreezeUserBook> breezeUserBookList = bookRepository.getListOfBookForUserUsingCode(userCode);
        if (CollectionUtils.isEmpty(breezeUserBookList)) {
            logger.info("No books found for user with code: " + userCode);
            bookListResponse.setBookDetailsList(new ArrayList<>());
            bookListResponse.setCount(0);
            return bookListResponse;
        }

        List<String> bookCodeList = breezeUserBookList.stream().map(BreezeUserBook::getCode).toList();

        List<BreezeBookDetails> breezeBookDetailsList = bookRepository.getListOfBooksUsingCode(bookCodeList);
        if (CollectionUtils.isEmpty(breezeBookDetailsList)) {
            logger.info("No books details found for books for user: " + userCode);
            bookListResponse.setBookDetailsList(new ArrayList<>());
            bookListResponse.setCount(0);
            return bookListResponse;
        }

        List<BreezeBookDetails> filteredBookDetailsList = breezeBookDetailsList.stream().filter(book -> book.getBookName().contains(bookName)).toList();
        bookListResponse = ModelToResponseConverter.getBookListResponseFromModel(filteredBookDetailsList);
        return bookListResponse;
    }

    @Override
    public BookListResponse getBooksByAuthorForUser(String authorName, String userCode) throws BreezeException {
        BookListResponse bookListResponse = new BookListResponse();

        if (!StringUtils.hasText(authorName)) {
            logger.error("Author name in request cannot null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_AUTHOR_NAME_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_AUTHOR_NAME_IN_REQUEST_MSG);
        }

        if (!StringUtils.hasText(userCode)) {
            logger.error("User code in request cannot null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_USER_CODE_IN_REQUEST_CODE,
                    BreezeErrorCodes.INVALID_USER_CODE_IN_REQUEST_MSG);
        }

        List<BreezeUserBook> breezeUserBookList = bookRepository.getListOfBookForUserUsingCode(userCode);
        if (CollectionUtils.isEmpty(breezeUserBookList)) {
            logger.info("No books found for user with code: " + userCode);
            bookListResponse.setBookDetailsList(new ArrayList<>());
            bookListResponse.setCount(0);
            return bookListResponse;
        }

        List<String> bookCodeList = breezeUserBookList.stream().map(BreezeUserBook::getCode).toList();

        List<BreezeBookDetails> breezeBookDetailsList = bookRepository.getListOfBooksUsingCode(bookCodeList);
        if (CollectionUtils.isEmpty(breezeBookDetailsList)) {
            logger.info("No books details found for books for user: " + userCode);
            bookListResponse.setBookDetailsList(new ArrayList<>());
            bookListResponse.setCount(0);
            return bookListResponse;
        }

        List<BreezeBookDetails> filteredBookDetailsList = breezeBookDetailsList.stream().filter(book -> book.getAuthorName().contains(authorName)).toList();
        bookListResponse = ModelToResponseConverter.getBookListResponseFromModel(filteredBookDetailsList);
        return bookListResponse;
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

        Long currentReviewCount = breezeBookDetails.getReviewCount();
        BigDecimal currentRating = breezeBookDetails.getUserRating();
        BigDecimal updatedRating = currentRating.add(BigDecimal.valueOf(request.getRating())).divide(BigDecimal.valueOf(currentReviewCount + 1), 2, RoundingMode.HALF_UP);

        breezeBookDetails.setUserRating(updatedRating);
        breezeBookDetails.setReviewCount(currentReviewCount + 1);
        genericDao.update(breezeBookDetails);
    }

    private void setBookListFilters(FetchBookList request) {

        // Set default values if null
        if (request.getGenreList() == null) {
            request.setGenreList(Arrays.asList(BookGenre.values()));
        }
        if (request.getPages() == null) {
            request.setPages(new NoOfPages(BreezeConstants.MIN_PAGES, BreezeConstants.MAX_PAGES));
        } else {
            request.getPages().setDefaultIfNull();
        }
        if (request.getYob() == null) {
            request.setYob(new YearOfPublishing(BreezeConstants.YOP_START_DATE, BreezeConstants.YOP_END_DATE));
        } else {
            request.getYob().setDefaultDatesIfNull();
        }
    }
}

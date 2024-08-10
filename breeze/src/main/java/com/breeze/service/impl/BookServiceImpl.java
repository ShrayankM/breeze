package com.breeze.service.impl;

import com.breeze.constant.BreezeConstants;
import com.breeze.constant.BreezeConstants.BookGenre;
import com.breeze.dao.BookRepository;
import com.breeze.dao.GenericDao;
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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(BookServiceImpl.class);

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenericDao genericDao;

    @Override
    public BookListResponse getBooks(FetchBookList request) {
        BookListResponse bookListResponse = new BookListResponse();

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
    public BookListResponse getBooksForUser(FetchBookList request) {
        BookListResponse bookListResponse = new BookListResponse();

        if (request.getUserCode() == null) {
            logger.error("User code cannot be null or empty");
            return bookListResponse;
        }

        if (request.getBookStatus() == null) {
            logger.error("Book status cannot be null or empty");
            return bookListResponse;
        }

        setBookListFilters(request);

        List<BreezeUserBook> breezeUserBookList = bookRepository.getListOfBooksForUser(request.getUserCode(), request.getBookStatus());
        List<String> bookCodeList = breezeUserBookList.stream().map(BreezeUserBook::getCode).toList();

        List<BreezeBookDetails> breezeBookDetailsList = bookRepository.getListOfBooksUsingCodeList(bookCodeList, request.getGenreList(), request.getPages().getMinPages(),
                request.getPages().getMaxPages(), request.getYob().getStartDate(), request.getYob().getEndDate());
        bookListResponse = ModelToResponseConverter.getBookListResponseFromModel(breezeBookDetailsList);
        return bookListResponse;
    }

    @Override
    public BookDetailsResponse getBookDetails(String bookCode) {
        BookDetailsResponse bookDetailsResponse = new BookDetailsResponse();

        if (MiscUtils.isStringNullOrEmpty(bookCode)) {
            logger.error(" Book code cannot be null or empty");
        }

        BreezeBookDetails bookDetails = bookRepository.getBookDetailsUsingCode(bookCode);
        bookDetailsResponse = ModelToResponseConverter.getBookDetailsResponseFromModel(bookDetails);
        return bookDetailsResponse;
    }

    @Override
    public BookListResponse getBooksByName(String bookName) {
        BookListResponse bookListResponse = new BookListResponse();

        if (MiscUtils.isStringNullOrEmpty(bookName)) {
            logger.error("Book name cannot be null or empty");
        }

        List<BreezeBookDetails> bookDetailsList = bookRepository.getBooksByName(bookName);
        bookListResponse = ModelToResponseConverter.getBookListResponseFromModel(bookDetailsList);
        return bookListResponse;
    }

    @Override
    public BookListResponse getBooksByAuthor(String authorName) {
        BookListResponse bookListResponse = new BookListResponse();

        if (MiscUtils.isStringNullOrEmpty(authorName)) {
            logger.error("Author Name cannot be null or empty");
        }

        List<BreezeBookDetails> bookDetailsList = bookRepository.getBooksByAuthor(authorName);
        bookListResponse = ModelToResponseConverter.getBookListResponseFromModel(bookDetailsList);
        return bookListResponse;

    }

    @Override
    public BookListResponse getBooksByNameForUser(String bookName, String userCode) {
        BookListResponse bookListResponse = new BookListResponse();

        if (MiscUtils.isStringNullOrEmpty(bookName) || MiscUtils.isStringNullOrEmpty(userCode)) {
            logger.error("Book Name or User code cannot be null or empty");
        }

        List<BreezeUserBook> breezeUserBookList = bookRepository.getListOfBookForUserUsingCode(userCode);
        List<String> bookCodeList = breezeUserBookList.stream().map(BreezeUserBook::getCode).toList();

        List<BreezeBookDetails> breezeBookDetailsList = bookRepository.getListOfBooksUsingCode(bookCodeList);
        List<BreezeBookDetails> filteredBookDetailsList = breezeBookDetailsList.stream().filter(book -> book.getBookName().contains(bookName)).toList();
        bookListResponse = ModelToResponseConverter.getBookListResponseFromModel(filteredBookDetailsList);
        return bookListResponse;
    }

    @Override
    public BookListResponse getBooksByAuthorForUser(String authorName, String userCode) {
        BookListResponse bookListResponse = new BookListResponse();

        if (MiscUtils.isStringNullOrEmpty(authorName) || MiscUtils.isStringNullOrEmpty(userCode)) {
            logger.error("Author name or User code cannot be null or empty");
        }

        List<BreezeUserBook> breezeUserBookList = bookRepository.getListOfBookForUserUsingCode(userCode);
        List<String> bookCodeList = breezeUserBookList.stream().map(BreezeUserBook::getCode).toList();

        List<BreezeBookDetails> breezeBookDetailsList = bookRepository.getListOfBooksUsingCode(bookCodeList);
        List<BreezeBookDetails> filteredBookDetailsList = breezeBookDetailsList.stream().filter(book -> book.getAuthorName().contains(authorName)).toList();
        bookListResponse = ModelToResponseConverter.getBookListResponseFromModel(filteredBookDetailsList);
        return bookListResponse;
    }

    @Override
    @Transactional
    public void updateBookRatingForUser(UpdateBookRating request) {

        if (MiscUtils.isStringNullOrEmpty(request.getUserCode()) || MiscUtils.isStringNullOrEmpty(request.getBookCode())) {
            logger.error("User code or book code cannot be null or empty");
        }

        if (request.getRating() == null || MiscUtils.isStringNullOrEmpty(request.getRating().toString())) {
            logger.error("Rating cannot be null or empty");
        }

        if (request.getRating() < 0 || request.getRating() > 5) {
            logger.error("Rating should be greater than 0 and less than 5");
        }

        BreezeUserBook breezeUserBook = bookRepository.getUserBookFromCode(request.getUserCode(), request.getBookCode());
        breezeUserBook.setUserRating(request.getRating());
        genericDao.update(breezeUserBook);

        BreezeBookDetails breezeBookDetails = bookRepository.getBookDetailsUsingCode(request.getBookCode());

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

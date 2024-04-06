package com.breeze.service.impl;

import com.breeze.constant.BreezeConstants;
import com.breeze.constant.BreezeConstants.BookGenre;
import com.breeze.dao.BookRepository;
import com.breeze.model.BreezeBookDetails;
import com.breeze.model.BreezeUserBook;
import com.breeze.request.FetchBookList;
import com.breeze.request.FetchBookList.YearOfPublishing;
import com.breeze.request.FetchBookList.NoOfPages;
import com.breeze.response.BookListResponse;
import com.breeze.service.BookService;
import com.breeze.util.LoggerWrapper;
import com.breeze.util.ModelToResponseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(BookServiceImpl.class);

    @Autowired
    BookRepository bookRepository;

    @Override
    public BookListResponse getBooks(FetchBookList request) {
        BookListResponse bookListResponse;

        setBookListFilters(request);

        List<BreezeBookDetails> breezeBookDetailsList = bookRepository.getListOfBooks(request.getGenreList(), request.getPages().getMinPages(),
                request.getPages().getMaxPages(), request.getYob().getStartDate(), request.getYob().getEndDate());

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

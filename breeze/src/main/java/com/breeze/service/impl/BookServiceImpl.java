package com.breeze.service.impl;

import com.breeze.constant.BreezeConstants;
import com.breeze.constant.BreezeConstants.BookGenre;
import com.breeze.dao.BookRepository;
import com.breeze.model.BreezeBookDetails;
import com.breeze.request.FetchBookList;
import com.breeze.request.FetchBookList.YearOfPublishing;
import com.breeze.request.FetchBookList.NoOfPages;
import com.breeze.response.BookListResponse;
import com.breeze.service.BookService;
import com.breeze.util.ModelToResponseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public BookListResponse getBooks(FetchBookList request) {
        BookListResponse bookListResponse;

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

        List<BreezeBookDetails> breezeBookDetailsList = bookRepository.getListOfBooks(request.getGenreList(), request.getPages().getMinPages(),
                request.getPages().getMaxPages(), request.getYob().getStartDate(), request.getYob().getEndDate());

        bookListResponse = ModelToResponseConverter.getBookListResponseFromModel(breezeBookDetailsList);
        return bookListResponse;
    }
}

package com.breeze.service.impl;

import com.breeze.request.FetchBookList;
import com.breeze.response.BookList;
import com.breeze.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Override
    public BookList getBooks(FetchBookList request) {
        return new BookList();
    }
}

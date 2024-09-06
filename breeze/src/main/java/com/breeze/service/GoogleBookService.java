package com.breeze.service;

import com.breeze.exception.BreezeException;
import com.breeze.request.CreateBookRecords;
import com.breeze.response.BookListResponse;
import com.breeze.response.GoogleBookResponse;

public interface GoogleBookService {

    GoogleBookResponse getBookUsingIsbn(String isbn) throws BreezeException;

    BookListResponse createBookRecords(CreateBookRecords request) throws BreezeException;
}

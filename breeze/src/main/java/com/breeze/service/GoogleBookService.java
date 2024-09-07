package com.breeze.service;

import com.breeze.exception.BreezeException;
import com.breeze.request.CreateBookRecords;
import com.breeze.response.BookDataResponse;
import com.breeze.response.GetListResponse;
import com.breeze.response.GoogleBookResponse;

public interface GoogleBookService {

    GoogleBookResponse getBookUsingIsbn(String isbn) throws BreezeException;

    GetListResponse<BookDataResponse> createBookRecords(CreateBookRecords request) throws BreezeException;
}

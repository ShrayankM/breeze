package com.breeze.service;

import com.breeze.request.FetchBookList;
import com.breeze.response.BookDetailsResponse;
import com.breeze.response.BookListResponse;

public interface BookService {

    BookListResponse getBooks(FetchBookList request);

    BookListResponse getBooksForUser(FetchBookList request);

    BookDetailsResponse getBookDetails(String bookCode);

}

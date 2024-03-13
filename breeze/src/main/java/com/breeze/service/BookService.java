package com.breeze.service;

import com.breeze.request.FetchBookList;
import com.breeze.response.BookList;

public interface BookService {

    BookList getBooks(FetchBookList request);

}

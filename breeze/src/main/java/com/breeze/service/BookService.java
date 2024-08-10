package com.breeze.service;

import com.breeze.exception.BreezeException;
import com.breeze.request.FetchBookList;
import com.breeze.request.UpdateBookRating;
import com.breeze.response.BookDetailsResponse;
import com.breeze.response.BookListResponse;

public interface BookService {

    BookListResponse getBooks(FetchBookList request);

    BookListResponse getBooksForUser(FetchBookList request) throws BreezeException;

    BookDetailsResponse getBookDetails(String bookCode) throws BreezeException;

    BookListResponse getBooksByName(String bookName) throws BreezeException;

    BookListResponse getBooksByAuthor(String authorName) throws BreezeException;

    BookListResponse getBooksByNameForUser(String bookName, String userCode) throws BreezeException;

    BookListResponse getBooksByAuthorForUser(String authorName, String userCode) throws BreezeException;

    void updateBookRatingForUser(UpdateBookRating request) throws BreezeException;

}

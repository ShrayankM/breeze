package com.breeze.service;

import com.breeze.exception.BreezeException;
import com.breeze.request.FetchBookList;
import com.breeze.request.UpdateBookRating;
import com.breeze.response.BookDataResponse;
import com.breeze.response.BookDetailsResponse;
import com.breeze.response.GetListResponse;

public interface BookService {

    GetListResponse<BookDataResponse> getBooks(FetchBookList request);

    GetListResponse<BookDataResponse> getBooksForUser(FetchBookList request) throws BreezeException;

    BookDetailsResponse getBookDetails(String bookCode) throws BreezeException;

    GetListResponse<BookDataResponse> getBooksByName(String bookName) throws BreezeException;

    GetListResponse<BookDataResponse> getBooksByAuthor(String authorName) throws BreezeException;

    GetListResponse<BookDataResponse> getBooksByNameForUser(String bookName, String userCode) throws BreezeException;

    GetListResponse<BookDataResponse> getBooksByAuthorForUser(String authorName, String userCode) throws BreezeException;

    void updateBookRatingForUser(UpdateBookRating request) throws BreezeException;

}

package com.breeze.service;

import com.breeze.exception.BreezeException;
import com.breeze.model.BreezeBookDetails;
import com.breeze.request.FetchBookList;
import com.breeze.request.UpdateBookRating;
import com.breeze.response.BookDataResponse;
import com.breeze.response.BookDetailsResponse;
import com.breeze.response.GetListResponse;

public interface BookService {

    GetListResponse<BookDataResponse> getBooks(FetchBookList request);

    GetListResponse<BookDataResponse> getBooksForUser(FetchBookList request) throws BreezeException;

    GetListResponse<BookDataResponse> searchBooksByNameAndAuthor(String searchQuery) throws BreezeException;

    BookDetailsResponse getBookDetails(String bookCode) throws BreezeException;

    BookDetailsResponse getBookDetailsForUser(String bookCode, String userCode) throws BreezeException;

    GetListResponse<BookDataResponse> searchBooksByNameAndAuthorForUser(String searchQuery, String userCode) throws BreezeException;

    GetListResponse<BookDataResponse> searchWishlistedBooksByNameAndAuthorForUser(String searchQuery, String userCode) throws BreezeException;

    void updateBookRatingForUser(UpdateBookRating request) throws BreezeException;
}

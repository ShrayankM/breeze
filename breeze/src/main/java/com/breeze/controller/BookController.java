package com.breeze.controller;

import com.breeze.constant.BreezeUrlConstants;
import com.breeze.exception.BreezeException;
import com.breeze.request.BookDetailsRequest;
import com.breeze.request.FetchBookList;
import com.breeze.request.UpdateBookRating;
import com.breeze.response.BookDataResponse;
import com.breeze.response.BookDetailsResponse;
import com.breeze.response.CommonResponse;
import com.breeze.response.GetListResponse;
import com.breeze.service.BookService;
import com.breeze.util.CommonResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping(
            path = BreezeUrlConstants.GET_BOOKS,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<GetListResponse<BookDataResponse>>> getBooks(@RequestBody FetchBookList request) {

        GetListResponse<BookDataResponse> response = bookService.getBooks(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @PostMapping(
            path = BreezeUrlConstants.GET_BOOKS_FOR_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<GetListResponse<BookDataResponse>>> getBooksForUser(@RequestBody FetchBookList request) throws BreezeException {

        GetListResponse<BookDataResponse> response = bookService.getBooksForUser(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @PostMapping(
            path = BreezeUrlConstants.GET_BOOK_DETAILS,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<BookDetailsResponse>> getBooksDetails(@RequestBody BookDetailsRequest request) throws BreezeException {

        BookDetailsResponse response = bookService.getBookDetails(request.getBookCode(), request.getUserCode());
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @GetMapping(
            path = BreezeUrlConstants.GET_BOOK_DETAILS_FOR_USER,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<BookDetailsResponse>> getBookDetailsForUser(@PathVariable String bookCode, @PathVariable String userCode) throws BreezeException {

        BookDetailsResponse response = bookService.getBookDetailsForUser(bookCode, userCode);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @GetMapping(
            path = BreezeUrlConstants.SEARCH_BOOKS_BY_NAME_AND_AUTHOR,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<GetListResponse<BookDataResponse>>> searchBooksByNameAndAuthor(@PathVariable String searchQuery) throws BreezeException {

        GetListResponse<BookDataResponse> response = bookService.searchBooksByNameAndAuthor(searchQuery);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @GetMapping(
            path = BreezeUrlConstants.SEARCH_BOOKS_BY_NAME_AND_AUTHOR_FOR_USER,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<GetListResponse<BookDataResponse>>> searchBooksByNameAndAuthorForUser(@PathVariable String searchQuery, @PathVariable String userCode) throws BreezeException {

        GetListResponse<BookDataResponse> response = bookService.searchBooksByNameAndAuthorForUser(searchQuery, userCode);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @GetMapping(
            path = BreezeUrlConstants.SEARCH_WISHLISTED_BOOKS_BY_NAME_AND_AUTHOR_FOR_USER,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<GetListResponse<BookDataResponse>>> searchWishlistedBooksByNameAndAuthorForUser(@PathVariable String searchQuery, @PathVariable String userCode) throws BreezeException {

        GetListResponse<BookDataResponse> response = bookService.searchWishlistedBooksByNameAndAuthorForUser(searchQuery, userCode);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @PostMapping(
            path = BreezeUrlConstants.UPDATE_BOOK_RATING_FOR_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> updateBookRatingForUser(@RequestBody UpdateBookRating request) throws BreezeException {

        bookService.updateBookRatingForUser(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse("OK"), HttpStatus.OK);
    }
}

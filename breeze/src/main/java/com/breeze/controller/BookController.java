package com.breeze.controller;

import com.breeze.constant.BreezeUrlConstants;
import com.breeze.exception.BreezeException;
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

    @GetMapping(
            path = BreezeUrlConstants.GET_BOOK_DETAILS,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<BookDetailsResponse>> getBooksDetails(@PathVariable String bookCode) throws BreezeException {

        BookDetailsResponse response = bookService.getBookDetails(bookCode);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @GetMapping(
            path = BreezeUrlConstants.GET_BOOKS_BY_NAME,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<GetListResponse<BookDataResponse>>> getBooksByName(@PathVariable String bookName) throws BreezeException {

        GetListResponse<BookDataResponse> response = bookService.getBooksByName(bookName);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @GetMapping(
            path = BreezeUrlConstants.GET_BOOKS_BY_AUTHOR,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<GetListResponse<BookDataResponse>>> getBooksByAuthor(@PathVariable String authorName) throws BreezeException {

        GetListResponse<BookDataResponse> response = bookService.getBooksByAuthor(authorName);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @GetMapping(
            path = BreezeUrlConstants.GET_BOOKS_BY_NAME_FOR_USER,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<GetListResponse<BookDataResponse>>> getBooksByNameForUser(@PathVariable String bookName, @PathVariable String userCode) throws BreezeException {

        GetListResponse<BookDataResponse> response = bookService.getBooksByNameForUser(bookName, userCode);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @GetMapping(
            path = BreezeUrlConstants.GET_BOOKS_BY_AUTHOR_FOR_USER,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<GetListResponse<BookDataResponse>>> getBooksByAuthorForUser(@PathVariable String authorName, @PathVariable String userCode) throws BreezeException {

        GetListResponse<BookDataResponse> response = bookService.getBooksByAuthorForUser(authorName, userCode);
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

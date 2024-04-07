package com.breeze.controller;

import com.breeze.constant.BreezeUrlConstants;
import com.breeze.request.FetchBookList;
import com.breeze.response.BookDetailsResponse;
import com.breeze.response.BookListResponse;
import com.breeze.response.CommonResponse;
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

    @PostMapping(path = BreezeUrlConstants.GET_BOOKS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<BookListResponse>> getBooks(@RequestBody FetchBookList request) {

        BookListResponse response = bookService.getBooks(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @PostMapping(path = BreezeUrlConstants.GET_BOOKS_FOR_USER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<BookListResponse>> getBooksForUser(@RequestBody FetchBookList request) {

        BookListResponse response = bookService.getBooksForUser(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @GetMapping(path = BreezeUrlConstants.GET_BOOK_DETAILS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<BookDetailsResponse>> getBooksDetails(@PathVariable String bookCode) {

        BookDetailsResponse response = bookService.getBookDetails(bookCode);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @GetMapping(path = BreezeUrlConstants.GET_BOOKS_BY_NAME, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<BookListResponse>> getBooksByName(@PathVariable String bookName) {

        BookListResponse response = bookService.getBooksByName(bookName);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @GetMapping(path = BreezeUrlConstants.GET_BOOKS_BY_AUTHOR, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<BookListResponse>> getBooksByAuthor(@PathVariable String authorName) {

        BookListResponse response = bookService.getBooksByAuthor(authorName);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @GetMapping(path = BreezeUrlConstants.GET_BOOKS_BY_NAME_FOR_USER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<BookListResponse>> getBooksByNameForUser(@PathVariable String bookName, @PathVariable String userCode) {

        BookListResponse response = bookService.getBooksByNameForUser(bookName, userCode);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @GetMapping(path = BreezeUrlConstants.GET_BOOKS_BY_AUTHOR_FOR_USER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<BookListResponse>> getBooksByAuthorForUser(@PathVariable String authorName, @PathVariable String userCode) {

        BookListResponse response = bookService.getBooksByAuthorForUser(authorName, userCode);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }
}

package com.breeze.controller;

import com.breeze.constant.BreezeUrlConstants;
import com.breeze.request.FetchBookList;
import com.breeze.response.BookListResponse;
import com.breeze.response.CommonResponse;
import com.breeze.service.BookService;
import com.breeze.util.CommonResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping(path = BreezeUrlConstants.GET_BOOKS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<BookListResponse>> getBooks(@RequestBody FetchBookList request) {

        BookListResponse response = bookService.getBooks(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }
}

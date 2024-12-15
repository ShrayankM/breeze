package com.breeze.controller;

import com.breeze.constant.BreezeUrlConstants;
import com.breeze.exception.BreezeException;
import com.breeze.request.CreateBookRecords;
import com.breeze.response.BookDataResponse;
import com.breeze.response.CommonResponse;
import com.breeze.response.GetListResponse;
import com.breeze.response.GoogleBookResponse;
import com.breeze.service.GoogleBookService;
import com.breeze.util.CommonResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GoogleBookController {

    @Autowired
    GoogleBookService googleBookService;

    @PostMapping(
            path = BreezeUrlConstants.CREATE_BOOK_RECORDS,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<GetListResponse<BookDataResponse>>> createBookRecords(@RequestBody CreateBookRecords request) throws BreezeException {
        GetListResponse<BookDataResponse> response = googleBookService.createBookRecords(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @GetMapping(
            path = BreezeUrlConstants.GET_BOOK_FROM_GOOGLE_USING_ISBN,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<GoogleBookResponse>> getBookUsingIsbn(@PathVariable String isbn) throws BreezeException {
        GoogleBookResponse response = googleBookService.getBookUsingIsbn(isbn);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }
}

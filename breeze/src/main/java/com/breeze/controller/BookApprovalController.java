package com.breeze.controller;

import com.breeze.constant.BreezeUrlConstants;
import com.breeze.request.FetchBookApprovalList;
import com.breeze.request.CreateBookApproval;
import com.breeze.request.UpdateBookApproval;
import com.breeze.response.BookApprovalList;
import com.breeze.response.CommonResponse;
import com.breeze.service.BookApprovalService;
import com.breeze.util.CommonResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookApprovalController {

    @Autowired
    private BookApprovalService bookApprovalService;

    @PostMapping(path = BreezeUrlConstants.CREATE_BOOK_APPROVAL_REQUEST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> createBookApprovalRequest(@RequestBody CreateBookApproval request) {
        bookApprovalService.createBookApprovalRequest(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse("OK"), HttpStatus.OK);
    }

    @PostMapping(path = BreezeUrlConstants.FETCH_BOOK_APPROVAL_REQUESTS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<BookApprovalList>> fetchBookApprovalRequests(@RequestBody FetchBookApprovalList request) {
        BookApprovalList response = bookApprovalService.fetchBookApprovalRequests(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @PostMapping(path = BreezeUrlConstants.UPDATE_BOOK_APPROVAL_REQUEST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> updateBookApprovalRequest(@RequestBody UpdateBookApproval request) {
        bookApprovalService.updateBookApprovalRequest(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse("OK"), HttpStatus.OK);
    }
}

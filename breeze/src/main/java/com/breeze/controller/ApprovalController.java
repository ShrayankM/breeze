package com.breeze.controller;

import com.breeze.constant.BreezeUrlConstants;
import com.breeze.request.BookApprovalListRequest;
import com.breeze.request.BookApprovalRequest;
import com.breeze.response.BookApprovalResponseList;
import com.breeze.response.CommonResponse;
import com.breeze.service.BookApprovalService;
import com.breeze.util.CommonResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApprovalController {

    @Autowired
    private BookApprovalService bookApprovalService;

    @PostMapping(path = BreezeUrlConstants.ADD_BOOK_APPROVAL_REQUEST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> addBookApprovalRequest(@RequestBody BookApprovalRequest request) {
        bookApprovalService.addBookApprovalRequest(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse("OK"), HttpStatus.OK);
    }

    @PostMapping(path = BreezeUrlConstants.GET_BOOK_APPROVAL_REQUEST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<BookApprovalResponseList>> getBookApprovalRequestList(@RequestBody BookApprovalListRequest request) {
        BookApprovalResponseList response = bookApprovalService.getBookApprovalRequestList(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }
}

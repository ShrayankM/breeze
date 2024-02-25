package com.breeze.controller;

import com.breeze.constant.BreezeUrlConstants;
import com.breeze.request.BookApprovalRequest;
import com.breeze.service.BookApprovalService;
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
    public ResponseEntity<String> addBookApprovalRequest(@RequestBody BookApprovalRequest request) {

        bookApprovalService.addBookApprovalRequest(request);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}

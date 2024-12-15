package com.breeze.controller;

import com.breeze.constant.BreezeUrlConstants;
import com.breeze.exception.BreezeException;
import com.breeze.request.CreateUpdateUserBookRequest;
import com.breeze.response.CommonResponse;
import com.breeze.response.UserBookResponse;
import com.breeze.service.UserBookService;
import com.breeze.util.CommonResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserBookController {

    @Autowired
    private UserBookService userBookService;

    @PostMapping(
            path = BreezeUrlConstants.ADD_BOOK_FOR_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<UserBookResponse>> addBookForUser(
            @RequestBody CreateUpdateUserBookRequest request) throws BreezeException {

        UserBookResponse response = userBookService.addBookForUser(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @PostMapping(
            path = BreezeUrlConstants.UPDATE_BOOK_FOR_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<UserBookResponse>> updateBookForUser(
            @RequestBody CreateUpdateUserBookRequest request) throws BreezeException {

        UserBookResponse response = userBookService.updateBookForUser(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

}

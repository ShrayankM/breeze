package com.breeze.controller;

import com.breeze.constant.BreezeUrlConstants;
import com.breeze.exception.BreezeException;
import com.breeze.request.CreateUpdateUserBookRequest;
import com.breeze.request.CreateUpdateUserRequest;
import com.breeze.request.LoginUserRequest;
import com.breeze.response.CommonResponse;
import com.breeze.response.UserBookResponse;
import com.breeze.response.UserResponse;
import com.breeze.service.UserService;
import com.breeze.util.CommonResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(
            path = BreezeUrlConstants.CREATE_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<UserResponse>> createUser(
            @RequestBody CreateUpdateUserRequest request) throws BreezeException {

        UserResponse response = userService.createUser(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }

    @PostMapping(
            path = BreezeUrlConstants.LOGIN_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<UserResponse>> loginUser(
            @RequestBody LoginUserRequest request) throws BreezeException {

        UserResponse response = userService.loginUser(request);
        return new ResponseEntity<>(CommonResponseGenerator.okResponse(response), HttpStatus.OK);
    }
}

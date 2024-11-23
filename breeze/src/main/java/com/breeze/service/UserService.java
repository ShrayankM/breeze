package com.breeze.service;

import com.breeze.exception.BreezeException;
import com.breeze.request.CreateUpdateUserRequest;
import com.breeze.response.UserResponse;
import org.springframework.stereotype.Service;

public interface UserService {

    UserResponse createUser(CreateUpdateUserRequest request) throws BreezeException;
}

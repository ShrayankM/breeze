package com.breeze.service;

import com.breeze.exception.BreezeException;
import com.breeze.request.CreateUpdateUserBookRequest;
import com.breeze.response.UserBookResponse;

public interface UserBookService {

    UserBookResponse addBookForUser(CreateUpdateUserBookRequest request) throws BreezeException;
}

package com.breeze.service;

import com.breeze.response.GoogleBookResponse;


public interface GoogleRestApiService {

    GoogleBookResponse getBookDataUsingIsbn(String isbn);
}

package com.breeze.constant;

public class BreezeUrlConstants {

    private BreezeUrlConstants() {  super(); }

    public static final String URL_PREFIX = "/v1/breeze/";

    public static final String CREATE_BOOK_APPROVAL_REQUEST = URL_PREFIX + "approval/create-request";
//    public static final String FETCH_BOOK_APPROVAL_REQUEST = URL_PREFIX + "approval/fetch-request/{requestCode}";
    public static final String UPDATE_BOOK_APPROVAL_REQUEST = URL_PREFIX + "approval/update-request";
    public static final String FETCH_APPROVAL_REQUEST_LIST = URL_PREFIX + "approval/fetch-request-list";

    public static final String GET_BOOKS = URL_PREFIX + "book/get-books";
    public static final String GET_BOOKS_FOR_USER = URL_PREFIX + "book/get-books-user";
    public static final String GET_BOOK_DETAILS = URL_PREFIX + "book/{bookCode}/get-book-details";
    public static final String GET_BOOKS_BY_NAME = URL_PREFIX + "book/{bookName}/get-books";
    public static final String GET_BOOKS_BY_AUTHOR = URL_PREFIX + "book/{authorName}/get-books";
    public static final String GET_BOOKS_BY_NAME_FOR_USER = URL_PREFIX + "book/{bookName}/user/{userCode}/get-books";
    public static final String GET_BOOKS_BY_AUTHOR_FOR_USER = URL_PREFIX + "book/{authorName}/user/{userCode}/get-books";

    public static final String UPDATE_BOOK_RATING_FOR_USER = URL_PREFIX + "book/update-user-rating";
}

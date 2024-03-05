package com.breeze.constant;

public class BreezeUrlConstants {

    private BreezeUrlConstants() {  super(); }

    public static final String URL_PREFIX = "/v1/breeze/";

    public static final String CREATE_BOOK_APPROVAL_REQUEST = URL_PREFIX + "create-book-approval-request";
    public static final String FETCH_BOOK_APPROVAL_REQUESTS = URL_PREFIX + "fetch-book-approval-requests";
    public static final String UPDATE_BOOK_APPROVAL_REQUEST = URL_PREFIX + "update-book-approval-request";
}

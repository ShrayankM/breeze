package com.breeze.constant;

public class BreezeUrlConstants {

    private BreezeUrlConstants() {  super(); }

    public static final String URL_PREFIX = "/v1/breeze/";

    public static final String GET_BOOKS = URL_PREFIX + "book/get-books";
    public static final String GET_BOOKS_FOR_USER = URL_PREFIX + "book/get-books-user";
    public static final String GET_BOOK_DETAILS = URL_PREFIX + "book/get-book-details";
    public static final String GET_BOOK_DETAILS_FOR_USER = URL_PREFIX + "book/{bookCode}/user/{userCode}/get-book-details-user";
    public static final String SEARCH_BOOKS_BY_NAME_AND_AUTHOR = URL_PREFIX + "book/{searchQuery}/search-books";

    // user-apis
    public static final String CREATE_USER = URL_PREFIX + "user/create-user";
    public static final String LOGIN_USER = URL_PREFIX + "user/login-user";
    public static final String FETCH_USER_PROFILE = URL_PREFIX + "user/{userCode}/fetch-user-profile";

    // User-book apis
    public static final String ADD_BOOK_FOR_USER = URL_PREFIX + "book/add-book";
    public static final String UPDATE_BOOK_FOR_USER = URL_PREFIX + "book/update-book";

    // GOOGLE integration apis
    public static final String CREATE_BOOK_RECORDS = URL_PREFIX + "google/create-book-records";
    public static final String GET_BOOK_FROM_GOOGLE_USING_ISBN = URL_PREFIX + "google/{isbn}/get-book";

    public static final String SEARCH_BOOKS_BY_NAME_AND_AUTHOR_FOR_USER = URL_PREFIX + "book/{searchQuery}/user/{userCode}/search-books";
    public static final String SEARCH_WISHLISTED_BOOKS_BY_NAME_AND_AUTHOR_FOR_USER = URL_PREFIX + "book/wishlist/{searchQuery}/user/{userCode}/search-books";
    public static final String UPDATE_BOOK_RATING_FOR_USER = URL_PREFIX + "book/update-user-rating";
}

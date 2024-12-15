package com.breeze.constant;

public class BreezeErrorCodes {

    private BreezeErrorCodes(){};

    public static final int INVALID_USER_CODE = 1001;
    public static final String INVALID_USER_CODE_MSG = "User code in request is either null or empty";

    public static final int INVALID_BOOK_STATUS_IN_REQUEST_CODE = 1002;
    public static final String INVALID_BOOK_STATUS_IN_REQUEST_CODE_MSG = "Book status in request is either null or empty";

    public static final int INVALID_BOOK_CODE_IN_REQUEST_CODE = 1003;
    public static final String INVALID_BOOK_CODE_IN_REQUEST_CODE_MSG = "Book code in request is either null or empty";

//    public static final int INVALID_BOOK_NAME_IN_REQUEST_CODE = 1004;
//    public static final String INVALID_BOOK_NAME_IN_REQUEST_MSG = "Book name in request is either null or empty";

    public static final int INVALID_SEARCH_QUERY_IN_REQUEST_CODE = 1004;
    public static final String INVALID_SEARCH_QUERY_IN_REQUEST_MSG = "Search query in request is either null or empty";

//    public static final int INVALID_AUTHOR_NAME_IN_REQUEST_CODE = 1005;
//    public static final String INVALID_AUTHOR_NAME_IN_REQUEST_MSG = "Author name in request is either null or empty";

    public static final int INVALID_USER_CODE_IN_REQUEST_CODE = 1006;
    public static final String INVALID_USER_CODE_IN_REQUEST_MSG = "User code in request is either null or empty";

    public static final int INVALID_RATING_IN_REQUEST_CODE = 1007;
    public static final String INVALID_RATING_IN_REQUEST_MSG = "Rating in request is either null or empty";

    public static final int INVALID_REQUEST_TO_CREATE_BOOKS_CODE = 1010;
    public static final String INVALID_REQUEST_TO_CREATE_BOOKS_MSG = "Request sent to create-book records is null";

    public static final int INVALID_ISBN_LIST_IN_REQUEST_CODE = 1011;
    public static final String INVALID_ISBN_LIST_IN_REQUEST_MSG = "Isbn-list in request to create-book records is empty";

    public static final int USER_WITH_EMAIL_ALREADY_EXISTS_CODE = 1012;
    public static final String USER_WITH_EMAIL_ALREADY_EXISTS_MSG = "User with email already exists";

    public static final int USER_NOT_FOUND_ERROR_CODE = 1013;
    public static final String USER_NOT_FOUND_ERROR_MSG = "User not found";

    public static final int INVALID_PASSWORD_FOR_USER_ERROR_CODE = 1014;
    public static final String INVALID_PASSWORD_FOR_USER_ERROR_MSG = "Invalid password for user";


    // Data not found error codes
    public static final int DATA_NOT_FOUND = 2001;
    public static final String DATA_NOT_FOUND_MSG = "Data not found in DB";
}

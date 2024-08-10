package com.breeze.constant;

public class BreezeErrorCodes {

    private BreezeErrorCodes(){};

    public static final int INVALID_USER_CODE = 1001;
    public static final String INVALID_USER_CODE_MSG = "User code in request is either null or empty";

    public static final int INVALID_BOOK_STATUS_IN_REQUEST_CODE = 1002;
    public static final String INVALID_BOOK_STATUS_IN_REQUEST_CODE_MSG = "Book status in request is either null or empty";

    public static final int INVALID_BOOK_CODE_IN_REQUEST_CODE = 1003;
    public static final String INVALID_BOOK_CODE_IN_REQUEST_CODE_MSG = "Book code in request is either null or empty";

    public static final int INVALID_BOOK_NAME_IN_REQUEST_CODE = 1004;
    public static final String INVALID_BOOK_NAME_IN_REQUEST_MSG = "Book name in request is either null or empty";

    public static final int INVALID_AUTHOR_NAME_IN_REQUEST_CODE = 1005;
    public static final String INVALID_AUTHOR_NAME_IN_REQUEST_MSG = "Author name in request is either null or empty";

    public static final int INVALID_USER_CODE_IN_REQUEST_CODE = 1006;
    public static final String INVALID_USER_CODE_IN_REQUEST_MSG = "User code in request is either null or empty";

    public static final int INVALID_RATING_IN_REQUEST_CODE = 1007;
    public static final String INVALID_RATING_IN_REQUEST_MSG = "Rating in request is either null or empty";

    public static final int REJECTION_REASON_CANNOT_BE_NULL_OR_EMPTY_CODE = 1008;
    public static final String REJECTION_REASON_CANNOT_BE_NULL_OR_EMPTY_MSG = "Rejection reason cannot be null or empty for status update REJECTED";


    // Data not found error codes
    public static final int DATA_NOT_FOUND = 2001;
    public static final String DATA_NOT_FOUND_MSG = "Data not found in DB";
}

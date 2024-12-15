package com.breeze.constant;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class BreezeConstants {

    public enum ConfigDataType {
        STRING, LONG, BOOLEAN, DECIMAL, STRING_LIST
    }

    public enum UserType {
        ADMIN, STANDARD
    }

    public enum Status {
        ACTIVE, INACTIVE
    }

    public enum IsbnType {
        ISBN_10, ISBN_13
    }

    public enum BookStatus {
        WISHLIST, LIBRARY, READING, COMPLETED
    }

    public static final String BOOK_DETAILS_PREFIX = "BKD";
    public static final String USER_BOOK_PREFIX = "UBK";
    public static final String USER_PREFIX = "UER";
    public static final Long MIN_PAGES = 0L;
    public static final Long MAX_PAGES = 10000L;
    public static final Date YOP_START_DATE =  Date.from(LocalDate.of(1900, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    public static final Date YOP_END_DATE = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
}

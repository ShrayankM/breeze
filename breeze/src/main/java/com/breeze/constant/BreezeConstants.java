package com.breeze.constant;

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

    public enum BookGenre {
        FICTION, NON_FICTION, POETRY, DRAMA, ROMANCE, MYSTERY_THRILLER, SCIENCE_FICTION, FANTASY, HORROR, ADVENTURE, CHILDRENS_YOUNG_ADULT,
        HISTORICAL, BIOGRAPHY_AUTOBIOGRAPHY, HUMOR_SATIRE, DYSTOPIAN
    }

    public enum BookStatus {
        WISHLISTED, BOUGHT, READING, READ, COMPLETED
    }

    public enum UserSuggestionStatus {
        PENDING, SUBMITTED, CLOSED
    }

    public enum UserBookApprovalStatus {
        SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED
    }

    public static final String USER_BOOK_APPROVAL_PREFIX = "UBA";
}

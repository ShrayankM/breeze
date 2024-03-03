package com.breeze.constant;

public class BreezeConstants {

    public enum ConfigDataType {
        STRING, LONG, BOOLEAN, DECIMAL, STRING_LIST
    }

    public enum BreezeUserType {
        ADMIN, STANDARD
    }

    public enum BreezeStatus {
        ACTIVE, INACTIVE
    }

    public enum BreezeBookGenre {
        FICTION, NON_FICTION, POETRY, DRAMA, ROMANCE, MYSTERY_THRILLER, SCIENCE_FICTION, FANTASY, HORROR, ADVENTURE, CHILDRENS_YOUNG_ADULT,
        HISTORICAL, BIOGRAPHY_AUTOBIOGRAPHY, HUMOR_SATIRE, DYSTOPIAN
    }

    public enum BreezeBookStatus {
        WISHLISTED, BOUGHT, READING, READ, COMPLETED
    }

    public enum BreezeUserSuggestionStatus {
        PENDING, SUBMITTED, CLOSED
    }

    public enum BreezeUserApprovalStatus {
        PENDING, SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED
    }

    public static final String BOOK_APPROVAL_PREFIX = "BAR";
}

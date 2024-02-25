package com.breeze.constant;

public class BreezeConstants {

    public enum BreezeUserType {
        ADMIN, STANDARD
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
}

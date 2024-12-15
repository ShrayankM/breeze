package com.breeze.response;

import com.breeze.constant.BreezeConstants;
import lombok.Data;

@Data
public class UserBookResponse {

    private String bookCode;

    private String userCode;

    private BreezeConstants.BookStatus bookStatus;

    private Long currentPage;

    private Long userRating;

    private Boolean isDeleted;

    private Boolean wishlist;

    private BookDetailsResponse bookDetailsResponse;

    private String message;
}

package com.breeze.response;

import com.breeze.constant.BreezeConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDataResponse {

    private String code;

    private String name;

    private String subtitle;

    private String author;

    private String category;

    private String thumbnail;

    private BreezeConstants.BookStatus bookStatus;

    private Boolean isAddedToLibrary;

    private Boolean isAddedToWishlist;
}

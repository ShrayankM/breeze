package com.breeze.response;

import com.breeze.constant.BreezeConstants;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookDetailsResponse {

    private String bookName;

    private String isbn;

    private String authorName;

    private String s3ImageLink;

    private Date yearPublished;

    private Long noOfPages;

    private BreezeConstants.BookGenre bookGenre;

    private BigDecimal userRating;

    private String description;
}

package com.breeze.response;

import com.breeze.constant.BreezeConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDetailsResponse {

    private String name;

    private String author;

    private String thumbnail;

    private String publishedDate;

    private Long pages;

    private String category;

    private BigDecimal globalRating;

    private BigDecimal userRating;

    private String description;

    private BreezeConstants.BookStatus bookStatus;

    private String language;
}

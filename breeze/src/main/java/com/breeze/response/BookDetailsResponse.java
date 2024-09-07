package com.breeze.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookDetailsResponse {

    private String name;

    private String isbnSmall;

    private String isbnLarge;

    private String author;

    private String thumbnail;

    private Date publishedDate;

    private Long pages;

    private String category;

    private BigDecimal userRating;

    private String description;
}

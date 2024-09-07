package com.breeze.response;

import lombok.Data;

@Data
public class BookDataResponse {

    private String code;

    private String name;

    private String isbnSmall;

    private String isbnLarge;

    private String author;

    private String category;

    private String thumbnail;
}

package com.breeze.response;

import com.breeze.constant.BreezeConstants;
import lombok.Data;

@Data
public class BookDataResponse {

    private String code;

    private String name;

    private String subtitle;

    private String isbnSmall;

    private String isbnLarge;

    private String author;

    private String category;

    private String thumbnail;

    private BreezeConstants.BookStatus bookStatus;
}

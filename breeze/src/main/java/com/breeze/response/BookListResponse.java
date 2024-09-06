package com.breeze.response;

import lombok.Data;

import java.util.List;

@Data
public class BookListResponse {

    private List<BookData> bookDetailsList;

    private int count;

    @Data
    public static class BookData {

        private String code;

        private String name;

        private String isbnSmall;

        private String isbnLarge;

        private String author;

        private String category;

        private String thumbnail;
    }
}

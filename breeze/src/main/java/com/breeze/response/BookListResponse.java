package com.breeze.response;

import com.breeze.constant.BreezeConstants.BookGenre;
import lombok.Data;

import java.util.List;

@Data
public class BookListResponse {

    private List<BookData> bookDetailsList;

    private int count;

    @Data
    public static class BookData {

        private String bookName;

        private String isbn;

        private String authorName;

        private BookGenre genre;

        private String s3ImageLink;
    }
}

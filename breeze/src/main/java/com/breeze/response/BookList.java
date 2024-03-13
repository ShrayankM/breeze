package com.breeze.response;

import com.breeze.constant.BreezeConstants.BookGenre;
import lombok.Data;

import java.util.List;

@Data
public class BookList {

    private List<BookDetailsData> bookDetailsList;

    @Data
    public static class BookDetailsData {

        private String bookName;

        private String isbn;

        private String authorName;

        private BookGenre genre;

        private String s3ImageLink;
    }
}

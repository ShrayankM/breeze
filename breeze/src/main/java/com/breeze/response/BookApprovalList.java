package com.breeze.response;

import com.breeze.constant.BreezeConstants.BookGenre;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
public class BookApprovalList {

    private Integer count;

    private List<BookApprovalData> bookApprovalDataList;

    @Data
    @NoArgsConstructor
    public static class BookApprovalData {

        private String code;

        private String bookName;

        private String authorName;

        private String isbn;

        private Long noOfPages;

        private Date yearPublished;

        private BookGenre bookGenre;

        private String description;
    }
}

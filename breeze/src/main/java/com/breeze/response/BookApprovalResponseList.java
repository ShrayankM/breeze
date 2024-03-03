package com.breeze.response;

import com.breeze.constant.BreezeConstants.BreezeBookGenre;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
public class BookApprovalResponseList {

    private List<BookApprovalData> bookApprovalDataList;

    @Data
    @NoArgsConstructor
    public static class BookApprovalData {

        private String bookName;

        private String authorName;

        private String isbn;

        private Long noOfPages;

        private Date yearPublished;

        private BreezeBookGenre bookGenre;

        private String description;

    }

}

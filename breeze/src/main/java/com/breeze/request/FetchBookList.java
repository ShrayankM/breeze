package com.breeze.request;

import com.breeze.constant.BreezeConstants.BookGenre;
import com.breeze.constant.BreezeConstants.BookStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class FetchBookList {

    @NotBlank
    private String userCode;

    private BookStatus bookStatus;

    private BookGenre genre;

    private YearOfPublishing yob;

    private Long noOfPages;

    @Data
    public static class YearOfPublishing {

        private Date startDate;

        private Date endDate;
    }
}

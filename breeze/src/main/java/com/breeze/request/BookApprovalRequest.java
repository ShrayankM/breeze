package com.breeze.request;

import com.breeze.constant.BreezeConstants.BreezeBookGenre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class BookApprovalRequest {

    @NotBlank
    private String userCode;

    @NotBlank
    private BookData bookData;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    public static class BookData {

        private String bookName;

        private String authorName;

        private String isbn;

        private Long noOfPages;

        private Date yearPublished;

        private BreezeBookGenre bookGenre;

        private String description;
    }
}

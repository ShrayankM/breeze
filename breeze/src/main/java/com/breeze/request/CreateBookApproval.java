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
public class CreateBookApproval {

    @NotBlank
    private String userCode;

    @NotBlank
    private BookApprovalData bookApprovalData;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
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

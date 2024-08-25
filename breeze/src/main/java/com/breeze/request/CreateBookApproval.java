package com.breeze.request;

import com.breeze.constant.BreezeConstants.BookGenre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CreateBookApproval {

    @NotBlank(message = "User-code cannot be blank or empty in approval request")
    private String userCode;

    @NotBlank(message = "Data cannot be blank or empty in approval request")
    private BookApprovalData bookApprovalData;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    public static class BookApprovalData {

        @NotBlank(message = "Book name cannot be blank or empty in approval request")
        private String bookName;

        @NotBlank(message = "Author name cannot be blank or empty in approval request")
        private String authorName;

        @NotBlank(message = "ISBN cannot be blank or empty in approval request")
        private String isbn;

        private Long noOfPages;

        private Date yearPublished;

        @NotBlank(message = "Book Genre cannot be blank or empty in approval request")
        private BookGenre bookGenre;

        private String description;
    }
}

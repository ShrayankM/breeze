package com.breeze.request;

import com.breeze.constant.BreezeConstants;
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
    private String bookName;

    @NotBlank
    private String authorName;

    @NotBlank
    private String isbn;

    private Long noOfPages;

    private Date yearPublished;

    private BreezeConstants.BreezeBookGenre bookGenre;

    private String description;
}

package com.breeze.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateBookRating {

    @NotBlank
    private Long rating;

    @NotBlank
    private String userCode;

    @NotBlank
    private String bookCode;
}

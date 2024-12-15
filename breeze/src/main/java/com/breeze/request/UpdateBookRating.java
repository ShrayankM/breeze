package com.breeze.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateBookRating {

    @NotBlank
    @Min(value = 1, message = "Rating should be greater than or equal to 1")
    @Max(value = 5, message = "Rating should be less than or equal to 5")
    private Long rating;

    @NotBlank
    private String userCode;

    @NotBlank
    private String bookCode;
}

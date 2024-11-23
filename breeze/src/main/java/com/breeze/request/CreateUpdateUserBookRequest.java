package com.breeze.request;

import com.breeze.constant.BreezeConstants.BookStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CreateUpdateUserBookRequest {

    @NotNull
    private String bookCode;

    @NotNull
    private String userCode;

    private BookStatus bookStatus;

    private Long currentPage;

    private Long userRating;

    private Boolean isDeleted;

    private Boolean wishlist;
}

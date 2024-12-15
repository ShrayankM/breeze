package com.breeze.request;

import com.breeze.constant.BreezeConstants.BookStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class FetchBookList {

    @NotBlank(message = "User-code cannot be null or empty in request")
    private String userCode;

    private List<BookStatus> bookStatusList;

    @Min(value = 0)
    private int offset;

    @Min(value = 0)
    private int limit;
}

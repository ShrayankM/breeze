package com.breeze.request;

import com.breeze.constant.BreezeConstants;
import com.breeze.constant.BreezeConstants.BookStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class FetchBookList {

    @NotBlank(message = "User-code cannot be null or empty in request")
    private String userCode;

    private BookStatus bookStatus;

    private YearOfPublishing yob;

    private NoOfPages pages;

    @Min(value = 0)
    private int offset;

    @Min(value = 0)
    private int limit;

    @Data
    public static class NoOfPages {

        private Long minPages;

        private Long maxPages;

        public NoOfPages(Long minPages, Long maxPages) {
            this.minPages = minPages;
            this.maxPages = maxPages;
        }

        public void setDefaultIfNull() {
            this.minPages = this.minPages == null ? BreezeConstants.MIN_PAGES : this.minPages;
            this.maxPages = this.maxPages == null ? BreezeConstants.MAX_PAGES : this.maxPages;
        }
    }

    @Data
    public static class YearOfPublishing {

        private Date startDate;

        private Date endDate;

        public YearOfPublishing(Date startDate, Date endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public void setDefaultDatesIfNull() {
            this.startDate = this.startDate == null ? BreezeConstants.YOP_START_DATE : this.startDate;
            this.endDate = this.endDate == null ? BreezeConstants.YOP_END_DATE : this.endDate;
        }
    }
}

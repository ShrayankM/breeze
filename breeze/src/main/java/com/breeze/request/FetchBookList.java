package com.breeze.request;

import com.breeze.constant.BreezeConstants;
import com.breeze.constant.BreezeConstants.BookGenre;
import com.breeze.constant.BreezeConstants.BookStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FetchBookList {

    @NotBlank
    private String userCode;

    private BookStatus bookStatus;

    private List<BookGenre> genreList;

    private YearOfPublishing yob;

    private NoOfPages pages;

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

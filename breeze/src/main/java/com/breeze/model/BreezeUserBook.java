package com.breeze.model;

import com.breeze.constant.BreezeConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "breeze_user_book")
public class BreezeUserBook extends AbstractModelWithCode  {

    @Column(name = "book_code")
    private String bookCode;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "book_status", columnDefinition = "enum")
    @Enumerated(EnumType.STRING)
    private BreezeConstants.BookStatus bookStatus;

    @Column(name = "completed_count")
    private Long completedCount;

    @Column(name = "current_page")
    private Long currentPage;

    @Column(name = "user_rating")
    private Long userRating;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "wishlist")
    private Boolean wishlist;
}

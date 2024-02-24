package com.breeze.model;

import com.breeze.constant.BreezeConstants;
import jakarta.persistence.*;

@Entity
@Table(name = "breeze_user_book_details")
public class BreezeUserBookDetails extends AbstractModelWithCode  {

    @Column(name = "book_code")
    private String bookCode;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "book_status", columnDefinition = "enum")
    @Enumerated(EnumType.STRING)
    private BreezeConstants.BreezeBookStatus bookStatus;

    @Column(name = "current_page")
    private Long currentPage;
}

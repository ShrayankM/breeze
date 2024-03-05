package com.breeze.model;


import com.breeze.constant.BreezeConstants.BookGenre;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "breeze_book_details")
public class BreezeBookDetails extends AbstractModelWithCode {

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "s3_image_link")
    private String s3ImageLink;

    @Column(name = "year_published")
    private String yearPublished;

    @Column(name = "no_of_pages")
    private String noOfPages;

    @Column(name = "book_genre", columnDefinition = "enum")
    @Enumerated(EnumType.STRING)
    private BookGenre bookGenre;

    @Column(name = "user_rating")
    private BigDecimal userRating;

    @Column(name = "description")
    private String description;
}

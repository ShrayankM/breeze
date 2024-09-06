package com.breeze.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "breeze_book_details")
public class BreezeBookDetails extends AbstractModelWithCode {

    @Column(name = "name")
    private String name;

    @Column(name = "isbn_10")
    private String isbn10;

    @Column(name = "isbn_13")
    private String isbn13;

    @Column(name = "author")
    private String author;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "small_thumbnail")
    private String smallThumbnail;

    @Column(name = "published_date")
    private Date publishedDate;

    @Column(name = "pages")
    private Long pages;

    @Column(name = "category")
    private String category;

    @Column(name = "user_rating")
    private BigDecimal userRating;

    @Column(name = "review_count")
    private Long reviewCount;

    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BreezeBookDetails that = (BreezeBookDetails) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

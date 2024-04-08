package com.breeze.model;


import com.breeze.constant.BreezeConstants.BookGenre;
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

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "s3_image_link")
    private String s3ImageLink;

    @Column(name = "year_published")
    private Date yearPublished;

    @Column(name = "no_of_pages")
    private Long noOfPages;

    @Column(name = "book_genre", columnDefinition = "enum")
    @Enumerated(EnumType.STRING)
    private BookGenre bookGenre;

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

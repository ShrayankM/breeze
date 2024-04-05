package com.breeze.dao;

import com.breeze.constant.BreezeConstants.BookGenre;
import com.breeze.model.BreezeBookDetails;

import java.util.Date;
import java.util.List;

public interface BookRepository {

    List<BreezeBookDetails> getListOfBooks(List<BookGenre> genreList, Long minPages, Long maxPages, Date startDate, Date endDate);

}

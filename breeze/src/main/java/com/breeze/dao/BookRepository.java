package com.breeze.dao;

import com.breeze.constant.BreezeConstants.BookStatus;
import com.breeze.constant.BreezeConstants.BookGenre;
import com.breeze.model.BreezeBookDetails;
import com.breeze.model.BreezeUserBook;

import java.util.Date;
import java.util.List;

public interface BookRepository {

    List<BreezeBookDetails> getListOfBooks(List<BookGenre> genreList, Long minPages, Long maxPages, Date startDate, Date endDate);

    List<BreezeUserBook> getListOfBooksForUser(String userCode, BookStatus bookStatus);

    List<BreezeBookDetails> getListOfBooksUsingCodeList(List<String> bookCodeList, List<BookGenre> genreList, Long minPages, Long maxPages, Date startDate, Date endDate);

}

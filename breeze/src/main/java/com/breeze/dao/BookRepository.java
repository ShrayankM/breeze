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

    BreezeBookDetails getBookDetailsUsingCode(String bookCode);

    List<BreezeBookDetails> getBooksByName(String bookName);

    List<BreezeBookDetails> getBooksByAuthor(String authorName);

    List<BreezeUserBook> getListOfBookForUserUsingCode(String userCode);

    List<BreezeBookDetails> getListOfBooksUsingCode(List<String> bookCodeList);

    BreezeUserBook getUserBookFromCode(String userCode, String bookCode);

    List<BreezeBookDetails> getListOfBooksUsingIsbn(List<String> isbnList, Boolean isIsbn10);

}

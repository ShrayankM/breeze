package com.breeze.dao;

import com.breeze.constant.BreezeConstants.BookStatus;
import com.breeze.model.BreezeBookDetails;
import com.breeze.model.BreezeUserBook;
import com.breeze.request.FetchBookList;

import java.util.Date;
import java.util.List;

public interface BookRepository {

    List<BreezeBookDetails> getListOfBooks(FetchBookList request);

    List<BreezeUserBook> getListOfBooksForUser(String userCode, List<BookStatus> bookStatusList);

    List<BreezeBookDetails> getListOfBooksUsingCodeList(List<String> bookCodeList);

    BreezeBookDetails getBookDetailsUsingCode(String bookCode);

    List<BreezeBookDetails> getBooksByNameAndAuthor(String searchQuery);

    List<BreezeUserBook> getListOfBookForUserUsingCode(String userCode, List<BookStatus> bookStatusList);

    List<BreezeUserBook> getListOfUserBooksUsingBookCode(String bookCode);

    List<BreezeBookDetails> getListOfBooksUsingCodeAndNameOrAuthor(List<String> bookCodeList, String searchQuery);

    BreezeUserBook getUserBookFromCode(String userCode, String bookCode);

    List<BreezeBookDetails> getListOfBooksUsingIsbn(List<String> isbnList, Boolean isIsbn10);

    Long getTotalCountOfBooks();

}

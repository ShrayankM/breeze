package com.breeze.dao.impl;

import com.breeze.constant.BreezeConstants;
import com.breeze.constant.BreezeConstants.BookGenre;
import com.breeze.dao.BookRepository;
import com.breeze.model.BreezeBookDetails;
import com.breeze.model.BreezeUserBook;
import com.breeze.util.LoggerWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class BookRepositoryImpl extends GenericDaoImpl implements BookRepository {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(BookRepositoryImpl.class);
    @Override
    public List<BreezeBookDetails> getListOfBooks(List<BookGenre> genreList, Long minPages, Long maxPages, Date startDate, Date endDate) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT book FROM ")
                .append(BreezeBookDetails.class.getSimpleName())
                .append(" book ")
                .append(" WHERE book.bookGenre IN ( :genreList ) ")
                .append("AND ( book.noOfPages >= :minPages AND book.noOfPages <= :maxPages ) ")
                .append("AND ( book.yearPublished >= :startDate AND book.yearPublished <= :endDate ) ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("genreList", genreList);
        queryObject.setParameter("minPages", minPages);
        queryObject.setParameter("maxPages", maxPages);
        queryObject.setParameter("startDate", startDate);
        queryObject.setParameter("endDate", endDate);

        return queryObject.getResultList();
    }

    @Override
    public List<BreezeUserBook> getListOfBooksForUser(String userCode, BreezeConstants.BookStatus bookStatus) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT book FROM ")
                .append(BreezeUserBook.class.getSimpleName())
                .append(" book ")
                .append(" WHERE book.userCode = :userCode ")
                .append(" AND book.bookStatus = :bookStatus ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("userCode", userCode);
        queryObject.setParameter("bookStatus", bookStatus);
        return queryObject.getResultList();
    }

    @Override
    public List<BreezeBookDetails> getListOfBooksUsingCodeList(List<String> bookCodeList, List<BookGenre> genreList, Long minPages, Long maxPages, Date startDate, Date endDate) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT book FROM ")
                .append(BreezeBookDetails.class.getSimpleName())
                .append(" book ")
                .append(" WHERE book.code IN ( :bookCodeList ) ")
                .append(" AND book.bookGenre IN ( :genreList ) ")
                .append(" AND ( book.noOfPages >= :minPages AND book.noOfPages =< :maxPages ) ")
                .append(" AND (book.yearPublished >= :startDate AND book.yearPublished =< :endDate ) ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("bookCodeList", bookCodeList);
        queryObject.setParameter("genreList", genreList);
        queryObject.setParameter("minPages", minPages);
        queryObject.setParameter("maxPages", maxPages);
        queryObject.setParameter("startDate", startDate);
        queryObject.setParameter("endDate", endDate);

        return queryObject.getResultList();
    }

    @Override
    public BreezeBookDetails getBookDetailsUsingCode(String bookCode) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT book FROM ")
                .append(BreezeBookDetails.class.getSimpleName())
                .append(" book ")
                .append(" WHERE book.code = :bookCode ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("bookCode", bookCode);
        return (BreezeBookDetails) queryObject.getSingleResult();
    }

    @Override
    public List<BreezeBookDetails> getBooksByName(String bookName) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT book FROM ")
                .append(BreezeBookDetails.class.getSimpleName())
                .append(" book ")
                .append(" WHERE book.bookName LIKE :bookName ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();
        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("bookName", "%" + bookName + "%");
        return queryObject.getResultList();
    }

    @Override
    public List<BreezeBookDetails> getBooksByAuthor(String authorName) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT book FROM ")
                .append(BreezeBookDetails.class.getSimpleName())
                .append(" book ")
                .append(" WHERE book.authorName LIKE :authorName ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();
        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("authorName", "%" + authorName + "%");
        return queryObject.getResultList();
    }

    @Override
    public List<BreezeUserBook> getListOfBookForUserUsingCode(String userCode) {
        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT book FROM ")
                .append(BreezeUserBook.class.getSimpleName())
                .append(" book ")
                .append(" WHERE book.userCode = :userCode ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("userCode", userCode);
        return queryObject.getResultList();
    }

    @Override
    public List<BreezeBookDetails> getListOfBooksUsingCode(List<String> bookCodeList) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT book FROM ")
                .append(BreezeBookDetails.class.getSimpleName())
                .append(" book ")
                .append(" WHERE book.code IN ( :bookCodeList ) ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("bookCodeList", bookCodeList);
        return queryObject.getResultList();
    }

    @Override
    public BreezeUserBook getUserBookFromCode(String userCode, String bookCode) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT book FROM ")
                .append(BreezeUserBook.class.getSimpleName())
                .append(" book ")
                .append(" WHERE book.userCode = :userCode ")
                .append(" AND book.bookCode = :bookCode ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("userCode", userCode);
        queryObject.setParameter("bookCode", bookCode);
        return (BreezeUserBook) queryObject.getSingleResult();
    }
}

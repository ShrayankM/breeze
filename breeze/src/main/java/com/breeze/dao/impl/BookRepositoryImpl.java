package com.breeze.dao.impl;

import com.breeze.constant.BreezeConstants;
import com.breeze.dao.BookRepository;
import com.breeze.model.BreezeBookDetails;
import com.breeze.model.BreezeUserBook;
import com.breeze.request.FetchBookList;
import com.breeze.util.LoggerWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Repository
public class BookRepositoryImpl extends GenericDaoImpl implements BookRepository {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(BookRepositoryImpl.class);
    @Override
    public List<BreezeBookDetails> getListOfBooks(FetchBookList request) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT book FROM ")
                .append(BreezeBookDetails.class.getSimpleName())
                .append(" book ")
                .append(" WHERE ( book.pages >= :minPages AND book.pages <= :maxPages ) ")
                .append(" AND ( book.publishedDate >= :startDate AND book.publishedDate <= :endDate ) ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("minPages", request.getPages().getMinPages());
        queryObject.setParameter("maxPages", request.getPages().getMaxPages());
        queryObject.setParameter("startDate", request.getYob().getStartDate());
        queryObject.setParameter("endDate", request.getYob().getEndDate());

        if (request.getLimit() > 0) {
            queryObject.setMaxResults(request.getLimit());
        }

        if (request.getOffset() > 0) {
            queryObject.setFirstResult(request.getOffset());
        }
        return queryObject.getResultList();
    }

    @Override
    public List<BreezeUserBook> getListOfBooksForUser(String userCode, List<BreezeConstants.BookStatus> bookStatusList) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT book FROM ")
                .append(BreezeUserBook.class.getSimpleName())
                .append(" book ")
                .append(" WHERE book.userCode = :userCode ");

        if (!CollectionUtils.isEmpty(bookStatusList)) {
            queryBuilder.append(" AND book.bookStatus IN ( :bookStatusList ) ");
        }

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("userCode", userCode);
        queryObject.setParameter("bookStatusList", bookStatusList);
        return queryObject.getResultList();
    }

    @Override
    public List<BreezeBookDetails> getListOfBooksUsingCodeList(List<String> bookCodeList , Long minPages, Long maxPages, Date startDate, Date endDate) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT book FROM ")
                .append(BreezeBookDetails.class.getSimpleName())
                .append(" book ")
                .append(" WHERE book.code IN ( :bookCodeList ) ")
                .append(" AND ( book.pages >= :minPages AND book.pages <= :maxPages ) ")
                .append(" AND ( book.publishedDate >= :startDate AND book.publishedDate <= :endDate ) ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("bookCodeList", bookCodeList);
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

//    @Override
//    public List<BreezeBookDetails> getBooksByName(String bookName) {
//
//        StringBuilder queryBuilder = new StringBuilder().append(" ")
//                .append(" SELECT book FROM ")
//                .append(BreezeBookDetails.class.getSimpleName())
//                .append(" book ")
//                .append(" WHERE book.name LIKE :bookName ");
//
//        logger.debug("DB query = {}", queryBuilder.toString());
//
//        EntityManager entityManager = getEntityManager();
//        Query queryObject = entityManager.createQuery(queryBuilder.toString());
//        queryObject.setParameter("bookName", "%" + bookName + "%");
//        return queryObject.getResultList();
//    }

//    @Override
//    public List<BreezeBookDetails> getBooksByAuthor(String authorName) {
//
//        StringBuilder queryBuilder = new StringBuilder().append(" ")
//                .append(" SELECT book FROM ")
//                .append(BreezeBookDetails.class.getSimpleName())
//                .append(" book ")
//                .append(" WHERE book.author LIKE :authorName ");
//
//        logger.debug("DB query = {}", queryBuilder.toString());
//
//        EntityManager entityManager = getEntityManager();
//        Query queryObject = entityManager.createQuery(queryBuilder.toString());
//        queryObject.setParameter("authorName", "%" + authorName + "%");
//        return queryObject.getResultList();
//    }

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

//    @Override
//    public List<BreezeBookDetails> getListOfBooksUsingCode(List<String> bookCodeList) {
//
//        StringBuilder queryBuilder = new StringBuilder().append(" ")
//                .append(" SELECT book FROM ")
//                .append(BreezeBookDetails.class.getSimpleName())
//                .append(" book ")
//                .append(" WHERE book.code IN ( :bookCodeList ) ");
//
//        logger.debug("DB query = {}", queryBuilder.toString());
//
//        EntityManager entityManager = getEntityManager();
//
//        Query queryObject = entityManager.createQuery(queryBuilder.toString());
//        queryObject.setParameter("bookCodeList", bookCodeList);
//        return queryObject.getResultList();
//    }

    @Override
    public List<BreezeBookDetails> getListOfBooksUsingCodeAndNameOrAuthor(List<String> bookCodeList, String searchQuery) {
        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT book FROM ")
                .append(BreezeBookDetails.class.getSimpleName())
                .append(" book ")
                .append(" WHERE book.code IN ( :bookCodeList ) ")
                .append(" AND ( book.name LIKE :searchQuery OR book.author LIKE :searchQuery ) ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("bookCodeList", bookCodeList);
        queryObject.setParameter("searchQuery", "%" + searchQuery + "%");
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

        List<BreezeUserBook> resultList = queryObject.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<BreezeBookDetails> getListOfBooksUsingIsbn(List<String> isbnList, Boolean isIsbn10) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT book FROM ")
                .append(BreezeBookDetails.class.getSimpleName())
                .append(" book ");

        if(isIsbn10) {
            queryBuilder.append(" WHERE book.isbn10 IN ( :isbnList ) ");
        } else {
            queryBuilder.append(" WHERE book.isbn13 IN ( :isbnList ) ");
        }

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("isbnList", isbnList);

        return queryObject.getResultList();
    }

    @Override
    public List<BreezeBookDetails> getBooksByNameAndAuthor(String searchQuery) {
        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT book FROM ")
                .append(BreezeBookDetails.class.getSimpleName())
                .append(" book ")
                .append(" WHERE book.name LIKE :searchQuery OR book.author LIKE :searchQuery ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();
        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("searchQuery", "%" + searchQuery + "%");
        return queryObject.getResultList();
    }
}

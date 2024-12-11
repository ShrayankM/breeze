package com.breeze.dao.impl;

import com.breeze.dao.UserRepository;
import com.breeze.model.BreezeUser;
import com.breeze.model.BreezeUserBook;
import com.breeze.util.LoggerWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl  extends GenericDaoImpl implements UserRepository {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(UserRepositoryImpl.class);

    @Override
    public BreezeUser getUserByCode(String userCode) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT user FROM ")
                .append(BreezeUser.class.getSimpleName())
                .append(" user ")
                .append(" WHERE user.code = :userCode ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("userCode", userCode);
        return (BreezeUser) queryObject.getSingleResult();
    }

    @Override
    public BreezeUser getUserByEmail(String emailAddress) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT user FROM ")
                .append(BreezeUser.class.getSimpleName())
                .append(" user ")
                .append(" WHERE user.emailAddress = :emailAddress ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("emailAddress", emailAddress);

        List<BreezeUser> resultList = queryObject.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }
}

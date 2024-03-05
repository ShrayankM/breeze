package com.breeze.dao.impl;

import com.breeze.constant.BreezeConstants.UserBookApprovalStatus;
import com.breeze.dao.BookApprovalRepository;
import com.breeze.model.BreezeUserBookApproval;
import com.breeze.util.LoggerWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class BookApprovalRepositoryImpl extends GenericDaoImpl implements BookApprovalRepository {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(BookApprovalRepositoryImpl.class);
    @Override
    public List<BreezeUserBookApproval> getListOfApprovalRequests(UserBookApprovalStatus status) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT approval FROM ")
                .append(BreezeUserBookApproval.class.getSimpleName())
                .append(" approval ")
                .append(" WHERE approval.approvalStatus = :status ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("status", status);

        return (List<BreezeUserBookApproval>) queryObject.getResultList();
    }

    @Override
    public BreezeUserBookApproval getApprovalRequestFromCode(String code) {

        StringBuilder queryBuilder = new StringBuilder(" ")
                .append(" SELECT approval FROM ")
                .append(BreezeUserBookApproval.class.getSimpleName())
                .append(" approval ")
                .append(" WHERE approval.code = :code ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("code", code);

        List<BreezeUserBookApproval> resultList = queryObject.getResultList();
        return CollectionUtils.isEmpty(resultList) ? null : resultList.get(0);
    }
}

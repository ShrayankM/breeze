package com.breeze.dao.impl;

import com.breeze.constant.BreezeConstants.BreezeUserBookApprovalStatus;
import com.breeze.dao.BookApprovalRepository;
import com.breeze.model.BreezeUserBookApproval;
import com.breeze.util.LoggerWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookApprovalRepositoryImpl extends GenericDaoImpl implements BookApprovalRepository {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(BookApprovalRepositoryImpl.class);
    @Override
    public List<BreezeUserBookApproval> getListOfApprovalRequests(BreezeUserBookApprovalStatus status) {

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
}

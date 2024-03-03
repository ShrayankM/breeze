package com.breeze.dao.impl;

import com.breeze.constant.BreezeConstants.BreezeUserApprovalStatus;
import com.breeze.dao.BookApprovalRepository;
import com.breeze.model.BreezeUserApproval;
import com.breeze.util.LoggerWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookApprovalRepositoryImpl extends GenericDaoImpl implements BookApprovalRepository {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(BookApprovalRepositoryImpl.class);
    @Override
    public List<BreezeUserApproval> getListOfApprovalRequests(BreezeUserApprovalStatus status) {

        StringBuilder queryBuilder = new StringBuilder().append(" ")
                .append(" SELECT approval FROM ")
                .append(BreezeUserApproval.class.getSimpleName())
                .append(" approval ")
                .append(" WHERE approval.approvalStatus = :status ");

        logger.debug("DB query = {}", queryBuilder.toString());

        EntityManager entityManager = getEntityManager();

        Query queryObject = entityManager.createQuery(queryBuilder.toString());
        queryObject.setParameter("status", status);

        return (List<BreezeUserApproval>) queryObject.getResultList();
    }
}

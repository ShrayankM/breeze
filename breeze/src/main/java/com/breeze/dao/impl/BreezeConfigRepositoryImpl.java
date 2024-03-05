package com.breeze.dao.impl;

import com.breeze.constant.BreezeConstants.BreezeStatus;
import com.breeze.dao.BreezeConfigRepository;
import com.breeze.model.BreezeConfig;
import com.breeze.util.LoggerWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BreezeConfigRepositoryImpl extends GenericDaoImpl implements BreezeConfigRepository {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(BreezeConfigRepositoryImpl.class);

    @Override
    public List<BreezeConfig> findAllByStatus(BreezeStatus status) {

        StringBuilder queryBuilder = new StringBuilder(" ")
                .append(" SELECT config FROM ")
                .append(BreezeConfig.class.getSimpleName())
                .append(" config ")
                .append(" WHERE config.status = :status ");

        logger.debug("DB Query: {} ", queryBuilder.toString());

        EntityManager em = getEntityManager();
        Query queryObject = em.createQuery(queryBuilder.toString());

        queryObject.setParameter("status", status);
        return (List<BreezeConfig>) queryObject.getResultList();

    }
}

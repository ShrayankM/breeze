package com.breeze.dao.impl;

import com.breeze.dao.GenericDao;
import com.breeze.model.AbstractModel;
import com.breeze.util.LoggerWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class GenericDaoImpl implements GenericDao {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(GenericDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    @Transactional
    public <T extends AbstractModel> T create(T entity) {
        try {
            EntityManager currentEm = getEntityManager();
            if (!currentEm.isJoinedToTransaction()) {
                currentEm.joinTransaction();
            }
            currentEm.persist(entity);
            currentEm.flush();
            return entity;
        } catch (Exception e) {
            logger.error("Exception = {} occurred while creating entity = {}", e, entity);
        }
        return null;
    }

    @Override
    @Transactional
    public <T extends AbstractModel> T update(T entity) {
        try {
            EntityManager currentEm = getEntityManager();
            if (!currentEm.isJoinedToTransaction()) {
                currentEm.joinTransaction();
            }
            currentEm.flush();
            T updatedEntity = currentEm.merge(entity);
            currentEm.flush();
            return updatedEntity;
        } catch (Exception e) {
            logger.error("Exception = {} occurred while updating entity = {}", e, entity);
        }
        return null;
    }

    @Override
    @Transactional
    public <T extends AbstractModel> List<T> createAll(Iterable<T> entities) {
        Assert.notNull(entities, "Entity list must not be null!");
        List<T> result = new ArrayList<>();
        for (T entity : entities) {
            result.add(this.create(entity));
        }
        return result;
    }

    @Override
    @Transactional
    public <T extends AbstractModel> List<T> updateAll(Iterable<T> entities) {
        Assert.notNull(entities, "Entity list must not be null!");
        List<T> result = new ArrayList<>();
        for (T entity : entities) {
            result.add(this.update(entity));
        }
        return result;
    }
}

package com.breeze.dao;

import com.breeze.model.AbstractModel;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface GenericDao {

    EntityManager getEntityManager();

    <T extends AbstractModel> T create(T entity);

    <T extends AbstractModel> T update(T entity);

    <T extends AbstractModel> List<T> createAll(Iterable<T> entities);

    <T extends AbstractModel> List<T> updateAll(Iterable<T> entities);
}

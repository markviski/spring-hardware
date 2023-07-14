package edu.bbte.idde.vmim1980.spring.dao;

import edu.bbte.idde.vmim1980.spring.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    T saveAndFlush(T entity);

    T getById(Long id);

    T save(T entity);

    void deleteById(Long id);

    Collection<T> findAll();
}

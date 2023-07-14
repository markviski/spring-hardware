package edu.bbte.idde.vmim1980.backend.dao;

import edu.bbte.idde.vmim1980.backend.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    void create(T entity);

    T read(Long id);

    void update(T entity, Long id);

    void delete(Long id);

    Collection<T> readAll();
}

/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao;

import java.util.List;

/**
 * Provides methods for generalized Dao interface
 */
public interface GenericDao<T> extends AutoCloseable {

    /**
     * Creates record in DB
     *
     * @param entity Entity to be stored in DB
     * @return Id of created record
     */
    int create(T entity);

    /**
     * Retrieves entity from DB by id
     *
     * @param id Id of the entity
     * @return Generalized entity
     */
    T findById(int id);

    /**
     * Retrieves all records of entity from DB
     *
     * @return List of generalized entities
     */
    List<T> findAll();

    /**
     * Updates entity in DB
     *
     * @param entity Entity to be updated in DB
     */
    void update(T entity);

    /**
     * Deletes entity from DB
     *
     * @param id Id of the entity to be deleted
     */
    void delete(int id);

    /**
     * Closes connection to DB
     */
    void close();
}

package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.AbstractEntity;

/**
 * The interface Update capable.
 * @param <T> The id type of updating entity.
 * @param <E> The type of updating entity.
 */
public interface UpdateCapable<T, E extends AbstractEntity<T>> {
    /**
     * Update entity.
     * @param id The id of the entity, that should be updated.
     * @param entity The entity, that characteristics of which will be taken to update.
     * @return The boolean. Equals true, if the updating succeed, otherwise false.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    boolean update(long id, E entity) throws DaoException;
}

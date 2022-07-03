package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.AbstractEntity;

import java.util.Optional;

/**
 * The interface Find and insert capable.
 * @param <T> The id type of finding(inserting) entity.
 * @param <E> The type of finding(inserting) entity.
 */
public interface FindInsertCapable<T, E extends AbstractEntity<T>> {
    /**
     * Find entity by id optional.
     * @return the optional
     * @throws DaoException - if the was any SQLException during database operations.
     */
    Optional<E> findById(T id) throws DaoException;

    /**
     * Insert entity.
     * @return The boolean. Equals true, if the insertion succeed, otherwise false.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    boolean insert(E entity) throws DaoException;
}

package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.AbstractEntity;

import java.util.List;

/**
 * The interface Find all capable.
 * @param <T> The id type of finding entity.
 * @param <E> The type of finding entity.
 */
public interface FindAllCapable<T, E extends AbstractEntity<T>> extends FindInsertCapable<T, E> {
    /**
     * Find all list.
     * @return the list
     * @throws DaoException - if the was any SQLException during database operations.
     */
    List<E> findAll() throws DaoException;
}

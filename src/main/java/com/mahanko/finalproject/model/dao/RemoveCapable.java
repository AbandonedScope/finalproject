package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;

/**
 * The interface Remove capable.
 * @param <T> The id type of removing entity.
 */
public interface RemoveCapable<T> {
    /**
     * Remove entity.
     * @param id The id of removing entity.
     * @return The boolean. Equals true, if the removing succeed, otherwise false.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    boolean remove(T id) throws DaoException;
}

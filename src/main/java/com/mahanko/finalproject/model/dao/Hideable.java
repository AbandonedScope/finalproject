package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;

/**
 * The interface Hideable.
 * @param <T> The id type of hiding entity.
 */
public interface Hideable<T> {
    /**
     * Update hidden status of the entity.
     * @param id The id of the entity to update hidden.
     * @param state The boolean. true - means that entity will not appear as result
     *             of found by name, or finding all operations(but still can be found be id).
     * @return The boolean. Equals true, if the updating succeed, otherwise false.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    boolean updateHidden(T id, boolean state) throws DaoException;
}

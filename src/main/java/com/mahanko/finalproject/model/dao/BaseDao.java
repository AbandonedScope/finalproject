package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.model.entity.AbstractEntity;

/**
 * The interface Base dao.
 * @param <T> The id type of finding, inserting, removing, updating entity.
 * @param <E> The type of finding, inserting, removing, updating entity.
 */
public interface BaseDao<T, E extends AbstractEntity<T>> extends FindAllCapable<T, E>,
        RemoveCapable<T>, UpdateCapable<T, E> {
}

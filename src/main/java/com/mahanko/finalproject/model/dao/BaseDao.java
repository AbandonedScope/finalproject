package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.model.entity.AbstractEntity;
import com.mahanko.finalproject.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T, E extends AbstractEntity<T>> {

    Optional<E> findById(long id) throws DaoException;

    boolean insert(E id) throws DaoException;

    boolean remove(T id) throws DaoException;

    List<E> findAll() throws DaoException;

    boolean update(long id, E entity) throws DaoException;
}

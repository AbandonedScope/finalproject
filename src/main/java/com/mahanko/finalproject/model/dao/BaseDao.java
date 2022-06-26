package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.model.entity.AbstractEntity;
import com.mahanko.finalproject.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BaseDao<E extends AbstractEntity> {

    Optional<E> findById(long id) throws DaoException;

    boolean insert(E e) throws DaoException;

    boolean remove(E e) throws DaoException;

    List<E> findAll() throws DaoException;

    boolean update(long id, E e) throws DaoException;
}

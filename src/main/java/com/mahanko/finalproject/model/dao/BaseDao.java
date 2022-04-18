package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.model.entity.AbstractEntity;
import com.mahanko.finalproject.exception.DaoException;

import java.util.List;

public interface BaseDao<E extends AbstractEntity> {

    boolean insert(E e) throws DaoException;

    boolean remove(E e) throws DaoException;

    List<E> findAll() throws DaoException;

    E update(long id, E e) throws DaoException;
}

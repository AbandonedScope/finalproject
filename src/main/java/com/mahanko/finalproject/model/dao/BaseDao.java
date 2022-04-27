package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.model.entity.AbstractEntity;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.IngredientComponent;

import java.util.List;
import java.util.Optional;

public interface BaseDao<E extends AbstractEntity> {

    Optional<E> findById(Long id) throws DaoException;

    boolean insert(E e) throws DaoException;

    boolean remove(E e) throws DaoException;

    List<E> findAll() throws DaoException;

    E update(long id, E e) throws DaoException;
}

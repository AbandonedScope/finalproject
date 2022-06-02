package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.Ingredient;

import java.util.List;

public interface IngredientDao extends BaseDao<Ingredient> {
    boolean existWithName(String name) throws DaoException;

    List<Ingredient> findByName(String name) throws DaoException;
}

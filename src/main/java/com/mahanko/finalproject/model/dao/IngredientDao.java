package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.Ingredient;

public interface IngredientDao extends BaseDao<Ingredient> {
    boolean existWithName(String name) throws DaoException;
}

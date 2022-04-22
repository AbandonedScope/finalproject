package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.IngredientComponent;

public interface IngredientDao extends BaseDao<IngredientComponent> {
    boolean existWithName(String name) throws DaoException;
}

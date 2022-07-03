package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.Ingredient;

import java.util.List;

/**
 * The interface Ingredient dao.
 */
public interface IngredientDao extends BaseDao<Long, Ingredient>, Hideable<Long> {
    /**
     * Check if exists any ingredient with the same name.
     * @param name the name to check.
     * @return The boolean. true - if ingredient with such name already exists in database, otherwise false.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    boolean existsWithName(String name) throws DaoException;

    /**
     * Find ingredients by name.
     * @param name the name to find ingredients by.
     * @return the list of the founded ingredients.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    List<Ingredient> findByName(String name) throws DaoException;

    /**
     * Checks if the ingredient with given id has a many-to-many relation with any menu item.
     * @param ingredientId the id of the ingredient to check.
     * @return The boolean. true - if the ingredient with given id has a many-to-many relation with any menu item, otherwise false.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    boolean existsMerge(long ingredientId) throws DaoException;
}

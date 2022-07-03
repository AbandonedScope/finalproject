package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.Ingredient;

import java.util.List;
import java.util.Optional;

/**
 * The interface IngredientService.
 */
public interface IngredientService {
    /**
     * Find ingredient by its id.
     *
     * @param id the id of the ingredient to find by.
     * @return If ingredient was found return ingredient optional, otherwise empty optional.
     * @throws ServiceException the service exception.
     */
    Optional<Ingredient> findById(Long id) throws ServiceException;

    /**
     * Find all ingredients.
     *
     * @return the list of the all ingredients.
     * @throws ServiceException the service exception.
     */
    List<Ingredient> findAll() throws ServiceException;

    /**
     * Find ingredients by name.
     *
     * @param name the name to find ingredients by.
     * @return the list of the founded ingredients.
     * @throws ServiceException the service exception.
     */
    List<Ingredient> findByName(String name) throws ServiceException;

    /**
     * Insert new ingredient into the database.
     *
     * @param parameters the parameters, must contain ingredient id, name, proteins, fats, carbohydrates, calories, picture.
     * @return If ingredient was inserted return ingredient optional, otherwise empty optional.
     * @throws ServiceException the service exception.
     */
    Optional<Ingredient> insert(RequestParameters parameters) throws ServiceException;

    /**
     * Remove ingredient from database, or if it is used by any menu item, set its hidden state to {@code true}.
     *
     * @param id the id of the ingredient to remove.
     * @return true, if the ingredient was removed(hidden).
     * @throws ServiceException the service exception.
     */
    boolean remove(long id) throws ServiceException;

    /**
     * Update ingredient information.
     *
     * @param id         the id of the ingredient to update.
     * @param parameters the parameters, must contain ingredient name, proteins, fats, carbohydrates, calories.
     * @return true, if the ingredient was updated.
     * @throws ServiceException the service exception.
     */
    boolean update(long id, RequestParameters parameters) throws ServiceException;
}

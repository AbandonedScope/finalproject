package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.MenuItem;

import java.util.List;
import java.util.Optional;

/**
 * The interface MenuItemService.
 */
public interface MenuItemService {
    /**
     * Find all menu items.
     *
     * @return the list of all menu items.
     * @throws ServiceException the service exception.
     */
    List<MenuItem> findAll() throws ServiceException;

    /**
     * Insert new menu item into database.
     *
     * @param parameters the parameters, must contain menu item name, cost, picture, section id, ingredients id and weights.
     * @return true, if menu item was inserted.
     * @throws ServiceException the service exception.
     */
    boolean insert(RequestParameters parameters) throws ServiceException;

    /**
     * Find menu item by its id.
     *
     * @param id the id of menu item to find.
     * @return If menu item was found return menu item optional, otherwise empty optional.
     * @throws ServiceException the service exception.
     */
    Optional<MenuItem> findById(Long id) throws ServiceException;

    /**
     * Find menu items by name.
     *
     * @param name the name to find menu items by.
     * @return the list of the founded menu items.
     * @throws ServiceException the service exception.
     */
    List<MenuItem> findByName(String name) throws ServiceException;

    /**
     * Update menu item information.
     *
     * @param id         the id of the menu item to update.
     * @param parameters the parameters, must contain menu item id, name, cost, section id, ingredients id and weights.
     * @return true, if the menu item was updated.
     * @throws ServiceException the service exception.
     */
    boolean update(long id, RequestParameters parameters) throws ServiceException;

    /**
     * Remove menu item from database, or if it is used in any order, set its hidden state to {@code true}.
     *
     * @param id the id of the menu item to remove.
     * @return true, if the menu item was removed(hidden).
     * @throws ServiceException the service exception.
     */
    boolean remove(long id) throws ServiceException;
}

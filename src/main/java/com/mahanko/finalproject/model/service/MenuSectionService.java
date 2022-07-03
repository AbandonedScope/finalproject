package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.MenuSection;

import java.util.List;

/**
 * The interface MenuSectionService.
 */
public interface MenuSectionService {
    /**
     * Find all menu sections.
     *
     * @return the list of all menu sections.
     * @throws ServiceException the service exception.
     */
    List<MenuSection> findAll() throws ServiceException;

    /**
     * Find menu sections by name.
     *
     * @param name the name to find menu sections by.
     * @return the list of the founded menu sections.
     * @throws ServiceException the service exception.
     */
    List<MenuSection> findByName(String name) throws ServiceException;

    /**
     * Find all menu sections, but not request for its menu items.
     *
     * @return the list of all menu sections.
     * @throws ServiceException the service exception.
     */
    List<MenuSection> findAllLazy() throws ServiceException;

    /**
     * Insert new menu section into database.
     *
     * @param params the parameters, must contain menu section name.
     * @return true, if menu section was inserted.
     * @throws ServiceException the service exception.
     */
    boolean insert(RequestParameters params) throws ServiceException;

    /**
     * Update menu section information.
     *
     * @param id         the id of the menu section to update.
     * @param parameters the parameters, must contain menu section id, name.
     * @return true, if the menu section was updated.
     * @throws ServiceException the service exception.
     */
    boolean update(int id, RequestParameters parameters) throws ServiceException;

    /**
     * Remove menu section from database, or if it contains any menu item, set its hidden state to {@code true}.
     *
     * @param id the id of the menu section to remove.
     * @return true, if the menu section was removed(hidden).
     * @throws ServiceException the service exception.
     */
    boolean remove(int id) throws ServiceException;
}

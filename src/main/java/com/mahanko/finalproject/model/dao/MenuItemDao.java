package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.MenuItem;

import java.util.List;

/**
 * The interface Menu item dao.
 */
public interface MenuItemDao extends BaseDao<Long, MenuItem>, Hideable<Long> {
    /**
     * Find all menu items that has a many-to-one relation with the menu section with given id.
     * @param sectionId the id of the menu section to find menu items many-to-one relation with.
     * @return the list of founded menu items.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    List<MenuItem> findBySectionId(long sectionId) throws DaoException;

    /**
     * Find menu items by name.
     * @param name the name to find menu items by.
     * @return the list of founded menu items.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    List<MenuItem> findByName(String name) throws DaoException;

    /**
     * Checks if the menu item with given id has a many-to-many relation with any order.
     * @param id the id of the menu item to check to.
     * @return The boolean. true - if the menu item with given id has a many-to-many relation with any order, otherwise false.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    boolean existsMerge(long id) throws DaoException;
}

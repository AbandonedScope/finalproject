package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.MenuSection;

import java.util.List;

/**
 * The interface Menu section dao.
 */
public interface MenuSectionDao extends BaseDao<Integer, MenuSection>, Hideable<Integer> {
    /**
     * Find menu sections by name.
     * @param name the name to find menu sections by.
     * @return the list of founded menu sections.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    List<MenuSection> findByName(String name) throws DaoException;

    /**
     * Checks if the menu section with given id has a one-to-many relation with any menu item.
     * @param sectionId the id of the menu section to check to.
     * @return The boolean. true - if the menu section with given id has a one-to-many relation with any menu item, otherwise false.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    boolean existsMerge(long sectionId) throws DaoException;
}

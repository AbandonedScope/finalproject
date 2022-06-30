package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.MenuItem;

import java.util.List;

public interface MenuItemDao extends BaseDao<Long, MenuItem>, Hideable<Long> {
    List<MenuItem> findBySectionId(long sectionId) throws DaoException;

    List<MenuItem> findByName(String name) throws DaoException;

    boolean existsMerge(long id) throws DaoException;
}

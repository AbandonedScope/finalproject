package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.MenuSection;

import java.util.List;

public interface MenuSectionDao extends BaseDao<Integer, MenuSection>, Hideable<Integer> {
    List<MenuSection> findByName(String name) throws DaoException;

    boolean existsMerge(long sectionId) throws DaoException;
}

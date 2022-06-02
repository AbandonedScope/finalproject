package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.MenuSection;

import java.util.List;

public interface MenuSectionDao extends BaseDao<MenuSection> {
    List<MenuSection> findByName(String name) throws DaoException;
}

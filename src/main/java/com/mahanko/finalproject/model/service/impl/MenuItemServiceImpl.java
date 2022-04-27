package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.dao.MenuItemDao;
import com.mahanko.finalproject.model.dao.impl.MenuItemDaoImpl;
import com.mahanko.finalproject.model.entity.menu.MenuItemComposite;
import com.mahanko.finalproject.model.service.MenuItemService;

import java.util.ArrayList;
import java.util.List;

public class MenuItemServiceImpl implements MenuItemService {
    @Override
    public List<MenuItemComposite> findAll() throws ServiceException {
        MenuItemDao menuItemDao = MenuItemDaoImpl.getInstance();
        List<MenuItemComposite> result;
        try {
            result = menuItemDao.findAll();
        } catch (DaoException e) {
            throw  new ServiceException(e);
        }

        return result;
    }

    @Override
    public boolean insertNew(MenuItemComposite menuItem) throws ServiceException {
        // FIXME: 22.04.2022 validation?
        boolean isInserted = false;
        MenuItemDao menuItemDao = MenuItemDaoImpl.getInstance();
        try {
            isInserted = menuItemDao.insert(menuItem);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return isInserted;
    }
}

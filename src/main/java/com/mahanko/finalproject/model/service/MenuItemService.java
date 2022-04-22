package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.MenuItemComposite;

public interface MenuItemService {
    boolean insertNew(MenuItemComposite menuItem) throws ServiceException;
}

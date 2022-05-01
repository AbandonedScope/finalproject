package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.MenuItemComposite;

import java.util.List;

public interface MenuItemService {
    List<MenuItemComposite> findAll() throws ServiceException;
    boolean insertNew(RequestParameters parameters) throws ServiceException;
}

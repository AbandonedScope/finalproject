package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.MenuSection;

import java.util.List;

public interface MenuSectionService {
    List<MenuSection> findAll() throws ServiceException;
}

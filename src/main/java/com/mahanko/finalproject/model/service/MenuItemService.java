package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemService {
    List<MenuItem> findAll() throws ServiceException;

    boolean insertNew(RequestParameters parameters) throws ServiceException;

    Optional<MenuItem> findById(Long id) throws ServiceException;

    List<MenuItem> findByName(String name) throws ServiceException;

    void update(long id, RequestParameters parameters) throws ServiceException;

    void remove(long id) throws ServiceException;
}

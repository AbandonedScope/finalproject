package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.IngredientComponent;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    Optional<IngredientComponent> findById(Long id) throws ServiceException;

    List<IngredientComponent> findAll() throws ServiceException;

    boolean insert(RequestParameters parameters) throws ServiceException;
}

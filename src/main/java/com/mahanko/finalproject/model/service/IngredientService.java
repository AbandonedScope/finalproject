package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    Optional<Ingredient> findById(Long id) throws ServiceException;

    List<Ingredient> findAll() throws ServiceException;

    boolean insert(RequestParameters parameters) throws ServiceException;
}

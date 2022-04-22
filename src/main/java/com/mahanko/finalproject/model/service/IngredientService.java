package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.IngredientComponent;

import java.util.List;

public interface IngredientService {
    List<IngredientComponent> findAll() throws ServiceException;
    boolean insert(IngredientComponent ingredient) throws ServiceException;
}

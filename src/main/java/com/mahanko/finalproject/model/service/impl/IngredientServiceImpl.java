package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.dao.IngredientDao;
import com.mahanko.finalproject.model.dao.impl.IngredientDaoImpl;
import com.mahanko.finalproject.model.entity.menu.IngredientComponent;
import com.mahanko.finalproject.model.service.IngredientService;

import java.util.List;
import java.util.Optional;

public class IngredientServiceImpl implements IngredientService {

    @Override
    public Optional<IngredientComponent> findById(Long id) throws ServiceException {
        IngredientComponent ingredient;
        Optional<IngredientComponent> ingredientOptional = Optional.empty();
        try {
            ingredientOptional = IngredientDaoImpl.getInstance().findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return ingredientOptional;
    }

    @Override
    public List<IngredientComponent> findAll() throws ServiceException {
        List<IngredientComponent> ingredients;
        try {
            ingredients = IngredientDaoImpl.getInstance().findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return ingredients;
    }

    @Override
    public boolean insert(IngredientComponent ingredient) throws ServiceException {
        boolean isInserted = false;
        // FIXME: 22.04.2022 what else???
        try {
            IngredientDao dao = IngredientDaoImpl.getInstance();
            if (!dao.existWithName(ingredient.getName())) {
                isInserted = dao.insert(ingredient);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return isInserted;
    }
}

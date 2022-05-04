package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.IngredientComponent;
import com.mahanko.finalproject.model.entity.menu.MenuItemComposite;
import com.mahanko.finalproject.model.entity.menu.MenuItemCompositeLevel;
import com.mahanko.finalproject.model.entity.menu.MenuSection;
import com.mahanko.finalproject.model.dao.MenuItemDao;
import com.mahanko.finalproject.model.dao.MenuSectionDao;
import com.mahanko.finalproject.model.dao.impl.MenuItemDaoImpl;
import com.mahanko.finalproject.model.dao.impl.MenuSectionDaoImpl;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.service.MenuItemService;
import com.mahanko.finalproject.validator.MenuItemValidator;
import com.mahanko.finalproject.validator.impl.IngredientValidatorImpl;
import com.mahanko.finalproject.validator.impl.MenuItemValidatorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MenuItemServiceImpl implements MenuItemService {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<MenuItemComposite> findAll() throws ServiceException {
        MenuItemDao menuItemDao = MenuItemDaoImpl.getInstance();
        List<MenuItemComposite> result;
        try {
            result = menuItemDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    @Override
    public boolean insertNew(RequestParameters parameters) throws ServiceException {
        boolean isInserted = false;
        try {
            String menuItemName = parameters.get(ParameterType.MENU_ITEM_NAME);
            String description = parameters.get(ParameterType.MENU_ITEM_DESCRIPTION);
            String menuItemPicture = parameters.get(ParameterType.MENU_ITEM_PICTURE);
            String pictureName = parameters.get(ParameterType.MENU_ITEM_PICTURE_NAME);
            long pictureSize = Long.parseLong(parameters.get(ParameterType.MENU_ITEM_PICTURE_SIZE));
            double menuItemCost = Double.parseDouble(parameters.get(ParameterType.MENU_ITEM_COST));
            long sectionId = Long.parseLong(parameters.get(ParameterType.MENU_ITEM_SECTION_ID));
            List<Double> ingredientWeights = parameters.getMultiple(ParameterType.INGREDIENT_WEIGHT).stream().map(Double::parseDouble).collect(Collectors.toList());
            List<Long> ingredientIds = parameters.getMultiple(ParameterType.INGREDIENT_ID).stream().map(Long::parseLong).collect(Collectors.toList());

            MenuSectionDao sectionDao = MenuSectionDaoImpl.getInstance();
            Optional<MenuSection> sectionOptional =  sectionDao.findById(sectionId);
            if (sectionOptional.isEmpty()) {
                logger.log(Level.ERROR, "Cant find section ith id : {}", sectionId);
                throw new ServiceException("Cant find section ith id : " + sectionId);
            }
            MenuSection section = sectionOptional.get();
            MenuItemValidator menuItemValidator = new MenuItemValidatorImpl();

            // FIXME: 01.05.2022 validation messages?
            if (menuItemValidator.validateName(menuItemName)
                    && menuItemValidator.validateCost(menuItemCost)
                    && menuItemValidator.validatePicture(pictureName, pictureSize)
                    && menuItemValidator.validateDescription(description)) {

                MenuItemComposite menuItem = new MenuItemComposite(MenuItemCompositeLevel.MENU_ITEM);
                menuItem.setName(menuItemName);
                menuItem.setCost(BigDecimal.valueOf(menuItemCost));
                menuItem.setPictureBase64(menuItemPicture);
                menuItem.setDescription(description);
                menuItem.setSection(section);
                IngredientService ingredientService = new IngredientServiceImpl(new IngredientValidatorImpl());
                for (int i = 0; i < ingredientIds.size(); i++) {
                    Optional<IngredientComponent> ingredientOptional = ingredientService.findById(ingredientIds.get(i));
                    if (ingredientOptional.isPresent()) {
                        IngredientComponent ingredient = ingredientOptional.get();
                        ingredient.setWeight(ingredientWeights.get(i));
                        menuItem.addIngredient(ingredient);
                    } else {
                        String message = String.format("Ingredient with id=%d for menu-item with name = \"%s\" was not found.", ingredientIds.get(i), menuItem.getName());
                        logger.log(Level.ERROR, message);
                        throw new ServiceException(message);
                    }
                }
                MenuItemDao menuItemDao = MenuItemDaoImpl.getInstance();
                isInserted = menuItemDao.insert(menuItem);
            }
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e);
        }

        return isInserted;
    }
}

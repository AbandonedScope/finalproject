package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.Ingredient;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import com.mahanko.finalproject.model.entity.menu.MenuSection;
import com.mahanko.finalproject.model.dao.MenuItemDao;
import com.mahanko.finalproject.model.dao.MenuSectionDao;
import com.mahanko.finalproject.model.dao.impl.MenuItemDaoImpl;
import com.mahanko.finalproject.model.dao.impl.MenuSectionDaoImpl;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.service.MenuItemService;
import com.mahanko.finalproject.model.validator.MenuItemValidator;
import com.mahanko.finalproject.model.validator.impl.MenuItemValidatorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mahanko.finalproject.controller.ParameterType.*;

public class MenuItemServiceImpl implements MenuItemService {
    private static final Logger logger = LogManager.getLogger();
    private static final MenuItemServiceImpl instance = new MenuItemServiceImpl();

    private MenuItemServiceImpl() {
    }

    public static MenuItemServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<MenuItem> findAll() throws ServiceException {
        MenuItemDao menuItemDao = MenuItemDaoImpl.getInstance();
        List<MenuItem> result;
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
        boolean isValid = true;
        try {
            String menuItemName = parameters.get(MENU_ITEM_NAME).trim();
            String description = parameters.get(MENU_ITEM_DESCRIPTION);
            String menuItemPicture = parameters.get(MENU_ITEM_PICTURE);
            String pictureName = parameters.get(MENU_ITEM_PICTURE_NAME);
            long pictureSize = Long.parseLong(parameters.get(MENU_ITEM_PICTURE_SIZE));
            double menuItemCost = Double.parseDouble(parameters.get(MENU_ITEM_COST));
            long sectionId = Long.parseLong(parameters.get(MENU_ITEM_SECTION_ID));
            List<Double> ingredientWeights = parameters.getMultiple(INGREDIENT_WEIGHT).stream().map(Double::parseDouble).collect(Collectors.toList());
            List<Long> ingredientIds = parameters.getMultiple(INGREDIENT_ID).stream().map(Long::parseLong).collect(Collectors.toList());

            MenuSectionDao sectionDao = MenuSectionDaoImpl.getInstance();
            Optional<MenuSection> sectionOptional = sectionDao.findById(sectionId);
            if (sectionOptional.isEmpty()) {
                logger.log(Level.ERROR, "Cant find section ith id : {}", sectionId);
                throw new ServiceException("Cant find section ith id : " + sectionId);
            }
            MenuSection section = sectionOptional.get();
            MenuItemValidator menuItemValidator = new MenuItemValidatorImpl();
            List<String> validationMessages = new ArrayList<>();

            if (!menuItemValidator.validateName(menuItemName)) {
                isValid = false;
                validationMessages.add(MEAL_NAME_VALIDATION_MESSAGE);
            }

            if (!menuItemValidator.validateCost(menuItemCost)) {
                isValid = false;
                validationMessages.add(MEAL_COST_VALIDATION_MESSAGE);
            }

            if (!menuItemValidator.validatePicture(pictureName, pictureSize)) {
                isValid = false;
                validationMessages.add(MEAL_PICTURE_VALIDATION_MESSAGE);
            }

            if (!menuItemValidator.validateDescription(description)) {
                isValid = false;
                validationMessages.add(MEAL_DESCRIPTION_VALIDATION_MESSAGE);
            }

            // FIXME: 01.05.2022 validation messages?
            if (isValid) {

                MenuItem menuItem = new MenuItem();
                menuItem.setName(menuItemName);
                menuItem.setCost(menuItemCost);
                menuItem.setPictureBase64(menuItemPicture);
                menuItem.setDescription(description);
                menuItem.setSection(section);
                IngredientService ingredientService = IngredientServiceImpl.getInstance();
                for (int i = 0; i < ingredientIds.size(); i++) {
                    Optional<Ingredient> ingredientOptional = ingredientService.findById(ingredientIds.get(i));
                    if (ingredientOptional.isPresent()) {
                        Ingredient ingredient = ingredientOptional.get();
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
            } else {
                parameters.put(VALIDATION_MESSAGES, validationMessages);
            }
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e);
        }

        return isInserted;
    }

    @Override
    public Optional<MenuItem> findById(Long id) throws ServiceException {
        Optional<MenuItem> optionalItem;
        MenuItemDao dao = MenuItemDaoImpl.getInstance();
        try {
            optionalItem = dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return optionalItem;
    }

    @Override
    public List<MenuItem> findByName(String name) throws ServiceException {
        List<MenuItem> items;
        MenuItemDao menuItemDao = MenuItemDaoImpl.getInstance();
        try {
            items = menuItemDao.findByName(name);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return items;
    }
}

package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.IngredientComponent;
import com.mahanko.finalproject.model.entity.menu.MenuItemComposite;
import com.mahanko.finalproject.model.entity.menu.MenuItemCompositeLevel;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.service.MenuItemService;
import com.mahanko.finalproject.model.service.impl.IngredientServiceImpl;
import com.mahanko.finalproject.model.service.impl.MenuItemServiceImpl;
import com.mahanko.finalproject.util.CustomStringEncoder;
import com.mahanko.finalproject.validator.MenuItemValidator;
import com.mahanko.finalproject.validator.impl.MenuItemValidatorImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AddMenuItemCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String MENU_ITEM_ADD_SUCCESS = "Menu item was added successfully.";
    private static final String MENU_ITEM_ADD_FAILED = "Menu item wasn't added. Probably item with such name already exists";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        MenuItemComposite menuItem = new MenuItemComposite(MenuItemCompositeLevel.MENU_ITEM);
        Router router = new Router(PagePath.ADD_INGREDIENT, Router.Type.FORWARD);
        try {
            // FIXME: 27.04.2022 checks
            List<Long> ingredientIds = Arrays.stream(request.getParameterValues(ParameterType.INGREDIENT_ID)).map(Long::parseLong).collect(Collectors.toList());
            List<Double> ingredientWeights = Arrays.stream(request.getParameterValues(ParameterType.INGREDIENT_WEIGHT)).map(Double::parseDouble).collect(Collectors.toList());
            Part filePart = request.getPart(ParameterType.MENU_ITEM_PICTURE);
            String pictureName = filePart.getSubmittedFileName();
            Long pictureSize = filePart.getSize();
            String menuItemName = request.getParameter(ParameterType.MENU_ITEM_NAME);
            double menuItemCost = Double.parseDouble(request.getParameter(ParameterType.MENU_ITEM_COST));
            String description = request.getParameter(ParameterType.MENU_ITEM_DESCRIPTION);
            MenuItemValidator menuItemValidator = new MenuItemValidatorImpl();

            if (menuItemValidator.validateName(menuItemName)
            && menuItemValidator.validateCost(menuItemCost)
            && menuItemValidator.validatePicture(pictureName, pictureSize)
            && menuItemValidator.validateDescription(description)) {

            String menuItemPicture = CustomStringEncoder.arrayToBase64(filePart.getInputStream().readAllBytes());
            menuItem.setName(menuItemName);
            menuItem.setCost(BigDecimal.valueOf(menuItemCost));
            menuItem.setPictureBase64(menuItemPicture);
            menuItem.setDescription(description);
            IngredientService ingredientService = new IngredientServiceImpl();
            MenuItemService menuItemService = new MenuItemServiceImpl();
            for (int i = 0; i < ingredientIds.size(); i++) {
                Optional<IngredientComponent> ingredientOptional = ingredientService.findById(ingredientIds.get(i));
                if (ingredientOptional.isPresent()) {
                    IngredientComponent ingredient = ingredientOptional.get();
                    ingredient.setWeight(ingredientWeights.get(i));
                    menuItem.addIngredient(ingredient);
                } else {
                    // FIXME: 26.04.2022
                    String message = String.format("Ingredient with id=%d for menu-item with name = \"%s\" was not found.", ingredientIds.get(i), menuItem.getName());
                    logger.log(Level.ERROR, message);
                    throw new CommandException(message);
                }
            }

            if (menuItemService.insertNew(menuItem)) {
                request.setAttribute(ParameterType.MENU_ITEM_ADD_MESSAGE, MENU_ITEM_ADD_SUCCESS);
            } else {
                request.setAttribute(ParameterType.MENU_ITEM_ADD_MESSAGE, MENU_ITEM_ADD_FAILED);
            }
            } else {
                // FIXME: 27.04.2022 some message
                request.setAttribute(ParameterType.MENU_ITEM_ADD_MESSAGE, "not valid params");
            }
            // FIXME: 27.04.2022 NullPointer, NumberFormat
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        }

        return router;
    }
}

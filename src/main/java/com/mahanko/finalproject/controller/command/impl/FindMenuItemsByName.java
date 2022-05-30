package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.Ingredient;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.service.MenuItemService;
import com.mahanko.finalproject.model.service.impl.IngredientServiceImpl;
import com.mahanko.finalproject.model.service.impl.MenuItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class FindMenuItemsByName implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router route = new Router(PagePath.MODIFY_MENU_ITEM, Router.Type.FORWARD);
        MenuItemService menuItemService = MenuItemServiceImpl.getInstance();
        IngredientService ingredientService = IngredientServiceImpl.getInstance();
        String menuItemName = request.getParameter(ParameterType.MENU_ITEM_NAME);
        List<MenuItem> items;
        List<Ingredient> ingredients;
        try {
            items = menuItemService.findByName(menuItemName);
            ingredients = ingredientService.findAll();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        request.setAttribute(ParameterType.MENU_ITEMS, items);
        request.setAttribute(ParameterType.INGREDIENTS, ingredients);
        return route;
    }
}

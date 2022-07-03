package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.Ingredient;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import com.mahanko.finalproject.model.entity.menu.MenuSection;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.service.MenuItemService;
import com.mahanko.finalproject.model.service.MenuSectionService;
import com.mahanko.finalproject.model.service.impl.IngredientServiceImpl;
import com.mahanko.finalproject.model.service.impl.MenuItemServiceImpl;
import com.mahanko.finalproject.model.service.impl.MenuSectionServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * The {@link Command} that is used to find menu item by its name.
 */
public class FindMenuItemsByNameCommand implements Command {
    /**
     * Executes a command.
     *
     * @param request  The request
     * @param response The resposce
     * @return  The router with type {@link Router.Type#FORWARD} to {@link PagePath#MODIFY_MENU_ITEM}.
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router route = new Router(PagePath.MODIFY_MENU_ITEM, Router.Type.FORWARD);
        MenuItemService menuItemService = MenuItemServiceImpl.getInstance();
        IngredientService ingredientService = IngredientServiceImpl.getInstance();
        MenuSectionService menuSectionService = MenuSectionServiceImpl.getInstance();
        String menuItemName = request.getParameter(ParameterType.MENU_ITEM_NAME);
        List<MenuItem> items;
        List<Ingredient> ingredients;
        List<MenuSection> sections;
        try {
            items = menuItemService.findByName(menuItemName);
            ingredients = ingredientService.findAll();
            sections = menuSectionService.findAllLazy();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        request.setAttribute(ParameterType.MENU_ITEMS, items);
        request.setAttribute(ParameterType.INGREDIENTS, ingredients);
        request.setAttribute(ParameterType.SECTIONS, sections);
        return route;
    }
}

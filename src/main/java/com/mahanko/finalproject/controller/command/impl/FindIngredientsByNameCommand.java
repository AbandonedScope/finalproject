package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.Ingredient;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.service.impl.IngredientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class FindIngredientsByNameCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router route = new Router(PagePath.MODIFY_INGREDIENT, Router.Type.FORWARD);
        String ingredientName = request.getParameter(ParameterType.INGREDIENT_NAME);
        IngredientService service = IngredientServiceImpl.getInstance();
        List<Ingredient> ingredients;
        try {
            ingredients = service.findByName(ingredientName);
            request.setAttribute(ParameterType.INGREDIENTS, ingredients);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return route;
    }
}

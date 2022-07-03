package com.mahanko.finalproject.controller.command.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mahanko.finalproject.controller.AttributeType;
import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.service.MenuSectionService;
import com.mahanko.finalproject.model.service.impl.IngredientServiceImpl;
import com.mahanko.finalproject.model.service.impl.MenuSectionServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The {@link Command} that find and put into request ingredients and sections.
 */
public class OnAddMenuItemPageCommand implements Command {
    /**
     * Executes a command.
     *
     * @param request  The request
     * @param response The responce
     * @return The router
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        IngredientService service = IngredientServiceImpl.getInstance();
        Router router = new Router(PagePath.ADD_MENU_ITEM, Router.Type.FORWARD);
        try {
            Gson gson = new GsonBuilder().create();
            String jsonIngredients = gson.toJson(service.findAll());
            MenuSectionService sectionService = MenuSectionServiceImpl.getInstance();
            request.setAttribute(AttributeType.SECTIONS, sectionService.findAllLazy());
            request.setAttribute(AttributeType.INGREDIENTS, jsonIngredients);

        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return router;
    }
}

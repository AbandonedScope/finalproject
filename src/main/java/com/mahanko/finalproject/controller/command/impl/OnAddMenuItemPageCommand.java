package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.service.impl.IngredientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OnAddMenuItemPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        IngredientService service = new IngredientServiceImpl();
        Router router = new Router(PagePath.ADD_MENU_ITEM, Router.Type.FORWARD);
        try {
            request.setAttribute(ParameterType.INGREDIENTS, service.findAll());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return router;
    }
}

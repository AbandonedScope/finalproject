package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.model.service.MenuSectionService;
import com.mahanko.finalproject.model.service.impl.MenuSectionServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddMenuSectionCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router route = new Router();
        RequestParameters parameters = new RequestParameters();
        parameters.put(ParameterType.MENU_SECTION_NAME, request.getParameter(ParameterType.MENU_SECTION_NAME));
        MenuSectionService service = new MenuSectionServiceImpl();
        service.
        return route;
    }
}

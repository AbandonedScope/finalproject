package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.MenuSectionService;
import com.mahanko.finalproject.model.service.impl.MenuSectionServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.mahanko.finalproject.controller.ParameterType.*;
import static com.mahanko.finalproject.controller.ValidationMessage.SECTION_ADD_FAIL_MESSAGE;
import static com.mahanko.finalproject.controller.ValidationMessage.SECTION_ADD_SUCCESSFULLY_MESSAGE;

public class AddMenuSectionCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router route = new Router(PagePath.ADD_MENU_SECTION, Router.Type.FORWARD);
        RequestParameters parameters = new RequestParameters();
        parameters.put(MENU_SECTION_NAME, request.getParameter(MENU_SECTION_NAME));
        MenuSectionService service = MenuSectionServiceImpl.getInstance();
        try {
            if(service.insert(parameters)) {
                request.setAttribute(SECTION_ADD_SUCCESSFULLY_MESSAGE, SECTION_ADD_SUCCESSFULLY_MESSAGE);
            } else {
                if (!parameters.fillRequestWithValidations(request)) {
                    // FIXME: 11.05.2022
                    request.setAttribute(SECTION_ADD_FAIL_MESSAGE, SECTION_ADD_FAIL_MESSAGE);
                }
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return route;
    }
}

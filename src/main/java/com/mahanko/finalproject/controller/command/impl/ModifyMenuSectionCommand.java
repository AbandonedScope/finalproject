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

public class ModifyMenuSectionCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = new Router(PagePath.MODIFY_MENU_SECTION, Router.Type.FORWARD);
        RequestParameters parameters = new RequestParameters();
        String sectionIdString = request.getParameter(MENU_SECTION_ID);
        parameters.put(MENU_SECTION_ID, sectionIdString);
        parameters.put(MENU_SECTION_NAME, request.getParameter(MENU_SECTION_NAME));
        MenuSectionService service = MenuSectionServiceImpl.getInstance();
        try {
            service.update(Integer.parseInt(sectionIdString), parameters);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException(e);
        }
        return router;
    }
}

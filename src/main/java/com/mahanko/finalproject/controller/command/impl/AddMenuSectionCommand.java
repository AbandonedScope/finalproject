package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.MenuSection;
import com.mahanko.finalproject.model.service.MenuSectionService;
import com.mahanko.finalproject.model.service.impl.MenuSectionServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static com.mahanko.finalproject.controller.ParameterType.*;
import static com.mahanko.finalproject.controller.ValidationMessage.*;

/**
 * The {@link AsynchronousCommand} that insert new {@link MenuSection} into database.
 */
public class AddMenuSectionCommand extends AsynchronousCommand {

    /**
     *  Executes a command.
     * @param request  The request
     * @param response The response
     * @return The router with type {@link Router.Type#NONE}
     * @throws CommandException the command exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router route;
        RequestParameters parameters = new RequestParameters();
        parameters.put(MENU_SECTION_NAME, request.getParameter(MENU_SECTION_NAME));
        MenuSectionService service = MenuSectionServiceImpl.getInstance();
        try {
            if(service.insert(parameters)) {
                route = fillResponse(response, true);
            } else {
                List<String> validationMessages = parameters.getMultiple(VALIDATION_MESSAGES);
                if (validationMessages == null) {
                    validationMessages = new ArrayList<>();
                    validationMessages.add(MENU_SECTION_WITH_SUCH_NAME_ALREADY_EXISTS_MESSAGE);
                }

                route = fillResponse(response, false);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return route;
    }
}

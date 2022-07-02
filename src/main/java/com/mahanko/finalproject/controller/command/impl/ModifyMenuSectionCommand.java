package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.ValidationMessage;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.MenuSection;
import com.mahanko.finalproject.model.service.MenuSectionService;
import com.mahanko.finalproject.model.service.impl.MenuSectionServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static com.mahanko.finalproject.controller.ParameterType.*;

/**
 * The {@link AsynchronousCommand} that update information about existing in database {@link MenuSection}.
 */
public class ModifyMenuSectionCommand extends AsynchronousCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router;
        RequestParameters parameters = new RequestParameters();
        String sectionIdString = request.getParameter(MENU_SECTION_ID);
        parameters.put(MENU_SECTION_ID, sectionIdString);
        parameters.put(MENU_SECTION_NAME, request.getParameter(MENU_SECTION_NAME));
        MenuSectionService service = MenuSectionServiceImpl.getInstance();
        try {
            boolean updated = service.update(Integer.parseInt(sectionIdString), parameters);
            List<String> validationMessages = parameters.getMultiple(ValidationMessage.VALIDATION_MESSAGES);
            router = validationMessages != null ?
                    fillResponse(response, updated, validationMessages)
                    : fillResponse(response, updated);
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (IOException | NumberFormatException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        }

        return router;
    }
}

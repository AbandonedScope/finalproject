package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.AttributeType;
import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.CustomerService;
import com.mahanko.finalproject.model.service.impl.CustomerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.mahanko.finalproject.controller.ParameterType.*;
import static com.mahanko.finalproject.controller.ValidationMessage.REGISTRATION_USER_EXISTS_MESSAGE;

public class AddUserCommand implements Command {
    
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router route = new Router();
        RequestParameters params = new RequestParameters();
        params.put(USER_NAME, request.getParameter(USER_NAME));
        params.put(USER_SURNAME, request.getParameter(USER_SURNAME));
        params.put(USER_LOGIN, request.getParameter(USER_LOGIN));
        params.put(USER_PASSWORD, request.getParameter(USER_PASSWORD));
        params.put(USER_CONFIRM_PASSWORD, request.getParameter(USER_CONFIRM_PASSWORD));
        // FIXME: 01.05.2022 validation messages
        try {
            CustomerService customerService = new CustomerServiceImpl();
            Optional<CustomerEntity> optionalCustomer = customerService.register(params);
            if (optionalCustomer.isEmpty()) {
                route.setPage(PagePath.REGISTRATION);
                route.setType(Router.Type.FORWARD);
                if (!params.fillRequestWithValidations(request)) {
                    // FIXME: 11.05.2022
                    request.setAttribute(REGISTRATION_USER_EXISTS_MESSAGE, REGISTRATION_USER_EXISTS_MESSAGE);
                }
            } else {
                request.getSession().setAttribute(AttributeType.USER, optionalCustomer.get());
                route.setPage(PagePath.MAIN);
            }

        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        }

        return route;
    }
}

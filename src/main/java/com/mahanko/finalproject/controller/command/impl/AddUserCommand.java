package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.CustomerService;
import com.mahanko.finalproject.model.service.impl.CustomerServiceImpl;
import com.mahanko.finalproject.validator.impl.CustomerValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class AddUserCommand implements Command {
    
    private static final Logger logger = LogManager.getLogger();
    
    private static final String REGISTRATION_VALIDATION_FAILED_MESSAGE = "Some field(s) is(are) wrong.";
    
    private static final String REGISTRATION_USER_EXISTS_MESSAGE = "Such user is already exists.";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        RequestParameters params = new RequestParameters();
        params.put(ParameterType.USER_NAME, request.getParameter(ParameterType.USER_NAME));
        params.put(ParameterType.USER_SURNAME, request.getParameter(ParameterType.USER_SURNAME));
        params.put(ParameterType.USER_LOGIN, request.getParameter(ParameterType.USER_LOGIN));
        params.put(ParameterType.USER_PASSWORD, request.getParameter(ParameterType.USER_PASSWORD));
        params.put(ParameterType.USER_CONFIRM_PASSWORD, request.getParameter(ParameterType.USER_CONFIRM_PASSWORD));
        // FIXME: 01.05.2022 validation messages
        try {
            Router route = new Router();
            CustomerService customerService = new CustomerServiceImpl(new CustomerValidatorImpl());
            Optional<CustomerEntity> optionalCustomer = customerService.register(params);
            if (optionalCustomer.isEmpty()) {
                route.setPage(PagePath.REGISTRATION);
                route.setType(Router.Type.FORWARD);
                request.setAttribute(ParameterType.REGISTRATION_VALIDATION_MESSAGE, REGISTRATION_USER_EXISTS_MESSAGE);
            } else {
                request.getSession().setAttribute(ParameterType.USER, optionalCustomer.get());
                route.setPage(PagePath.MAIN);
            }

            return route;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        }
    }
}

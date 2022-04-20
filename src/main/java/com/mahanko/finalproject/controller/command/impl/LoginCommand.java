package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.service.CustomerService;
import com.mahanko.finalproject.model.service.impl.CustomerServiceImpl;
import com.mahanko.finalproject.validator.CustomValidator;
import com.mahanko.finalproject.validator.impl.CustomValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String LOGIN_FAILED_MESSAGE = "Wrong login or password.";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String login = request.getParameter(ParameterType.LOGIN);
        String password = request.getParameter(ParameterType.PASSWORD);
        CustomerService customerService = CustomerServiceImpl.getInstance();
        String page;
        HttpSession session = request.getSession();
        Router route = new Router();
        CustomValidator validator = new CustomValidatorImpl();
        try {
            CustomerEntity customer;
            if (validator.validateLogin(login)
                    && validator.validatePassword(password)
                    && (customer = customerService.authenticate(login, password)) != null) {
                session.setAttribute(ParameterType.USER, customer);
                page = PagePath.MAIN;
            } else {
                request.setAttribute(ParameterType.LOGIN_VALIDATION_MESSAGE, LOGIN_FAILED_MESSAGE);
                page = PagePath.INDEX;
                route.setType(Router.Type.FORWARD);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        }

        route.setPage(page);
        return route;
    }
}

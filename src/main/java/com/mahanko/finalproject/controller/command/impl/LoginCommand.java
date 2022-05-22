package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.AttributeType;
import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.service.CustomerService;
import com.mahanko.finalproject.model.service.impl.CustomerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.mahanko.finalproject.controller.ParameterType.*;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String LOGIN_FAILED_MESSAGE = "Wrong login or password.";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String login = request.getParameter(USER_LOGIN);
        String password = request.getParameter(USER_PASSWORD);
        CustomerService customerService = new CustomerServiceImpl();
        String page;
        HttpSession session = request.getSession();
        Router route = new Router();
        // FIXME: 22.04.2022 validation messages?
        try {
            Optional<CustomerEntity> optionalCustomer = customerService.authenticate(login, password);
            if (optionalCustomer.isPresent()) {
                session.setAttribute(AttributeType.USER, optionalCustomer.get());
                page = PagePath.MAIN;
            } else {
                // FIXME: 11.05.2022
                request.setAttribute(LOGIN_VALIDATION_MESSAGE, LOGIN_FAILED_MESSAGE);
                page = PagePath.LOGIN;
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

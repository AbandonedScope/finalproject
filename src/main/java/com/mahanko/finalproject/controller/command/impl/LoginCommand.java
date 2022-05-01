package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.service.CustomerService;
import com.mahanko.finalproject.model.service.MenuItemService;
import com.mahanko.finalproject.model.service.impl.CustomerServiceImpl;
import com.mahanko.finalproject.model.service.impl.MenuItemServiceImpl;
import com.mahanko.finalproject.validator.CustomerValidator;
import com.mahanko.finalproject.validator.impl.CustomerValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String LOGIN_FAILED_MESSAGE = "Wrong login or password.";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String login = request.getParameter(ParameterType.USER_LOGIN);
        String password = request.getParameter(ParameterType.USER_PASSWORD);
        CustomerService customerService = new CustomerServiceImpl(new CustomerValidatorImpl());
        String page;
        HttpSession session = request.getSession();
        Router route = new Router();
        // FIXME: 22.04.2022 validation messages?
        try {
            Optional<CustomerEntity> optionalCustomer = customerService.authenticate(login, password);
            if (optionalCustomer.isPresent()) {
                session.setAttribute(ParameterType.USER, optionalCustomer.get());
                page = PagePath.MAIN;
                MenuItemService service = new MenuItemServiceImpl();
                session.setAttribute("menuItems", service.findAll());
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

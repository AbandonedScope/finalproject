package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.AttributeType;
import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.RequestParameters;
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

/**
 * The {@link Command} that is used to sign in customer by its login and password.
 */
public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String login = request.getParameter(USER_LOGIN);
        String password = request.getParameter(USER_PASSWORD);
        RequestParameters parameters = new RequestParameters();
        parameters.put(USER_LOGIN, login);
        parameters.put(USER_PASSWORD, password);
        String page;
        HttpSession session = request.getSession();
        Router route = new Router();
        CustomerService customerService =  CustomerServiceImpl.getInstance();
        try {
            Optional<CustomerEntity> optionalCustomer = customerService.authenticate(parameters);
            if (optionalCustomer.isPresent()) {
                session.setAttribute(AttributeType.USER, optionalCustomer.get());
                page = PagePath.MAIN;
            } else {
                parameters.fillRequestWithValidations(request);
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

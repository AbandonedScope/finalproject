package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.entity.RoleType;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.CustomerService;
import com.mahanko.finalproject.model.service.impl.CustomerServiceImpl;
import com.mahanko.finalproject.util.PasswordEncryptor;
import com.mahanko.finalproject.validator.CustomerValidator;
import com.mahanko.finalproject.validator.impl.CustomerValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class AddUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String REGISTRATION_VALIDATION_FAILED_MESSAGE = "Some field(s) is(are) wrong.";
    private static final String REGISTRATION_USER_EXISTS_MESSAGE = "Such user is already exists.";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String name = request.getParameter(ParameterType.USER_NAME);
        String surname = request.getParameter(ParameterType.USER_SURNAME);
        String login = request.getParameter(ParameterType.USER_LOGIN);
        String password = request.getParameter(ParameterType.USER_PASSWORD);
        String confirmPassword = request.getParameter(ParameterType.USER_CONFIRM_PASSWORD);
        Map<String, String> params = new HashMap<>(); // FIXME: 17.04.2022
        params.put(ParameterType.USER_NAME, name);
        params.put(ParameterType.USER_SURNAME, surname);
        params.put(ParameterType.USER_LOGIN, login);
        params.put(ParameterType.USER_PASSWORD, password);
        params.put(ParameterType.USER_CONFIRM_PASSWORD, confirmPassword);
        CustomerValidator validator = new CustomerValidatorImpl();
        try {
            Router route = new Router();
            if (!validator.validateRegistration(params)) {
                route.setPage(PagePath.REGISTRATION);
                route.setType(Router.Type.FORWARD);
                request.setAttribute(ParameterType.REGISTRATION_VALIDATION_MESSAGE, REGISTRATION_VALIDATION_FAILED_MESSAGE);
            } else {
                String encryptedPassword = PasswordEncryptor.encrypt(password);
                CustomerEntity customer = CustomerEntity.newBuilder()
                        .setName(name)
                        .setSurname(surname)
                        .setLogin(login)
                        .setPassword(encryptedPassword)
                        .setRole(RoleType.CUSTOMER)
                        .build();
                CustomerService customerService = CustomerServiceImpl.getInstance();
                if (!customerService.register(customer)) {
                    route.setPage(PagePath.REGISTRATION);
                    route.setType(Router.Type.FORWARD);
                    request.setAttribute(ParameterType.REGISTRATION_VALIDATION_MESSAGE, REGISTRATION_USER_EXISTS_MESSAGE);
                } else {
                    request.getSession().setAttribute(ParameterType.USER, customer);
                    route.setPage(PagePath.MAIN);
                }
            }

            return route;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        }
    }
}

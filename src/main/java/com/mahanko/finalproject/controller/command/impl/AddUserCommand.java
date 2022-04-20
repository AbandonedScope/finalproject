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
import com.mahanko.finalproject.validator.CustomValidator;
import com.mahanko.finalproject.validator.impl.CustomValidatorImpl;
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
        String name = request.getParameter(ParameterType.NAME);
        String surname = request.getParameter(ParameterType.SURNAME);
        String login = request.getParameter(ParameterType.LOGIN);
        String password = request.getParameter(ParameterType.PASSWORD);
        String confirmPassword = request.getParameter(ParameterType.CONFIRM_PASSWORD);
        Map<String, String> params = new HashMap<>(); // FIXME: 17.04.2022
        params.put(ParameterType.NAME, name);
        params.put(ParameterType.SURNAME, surname);
        params.put(ParameterType.LOGIN, login);
        params.put(ParameterType.PASSWORD, password);
        params.put(ParameterType.CONFIRM_PASSWORD, confirmPassword);
        CustomValidator validator = new CustomValidatorImpl();
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

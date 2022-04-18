package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.ParametersType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.entity.RoleType;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.CustomerService;
import com.mahanko.finalproject.model.service.RoleService;
import com.mahanko.finalproject.model.service.impl.CustomerServiceImpl;
import com.mahanko.finalproject.model.service.impl.RoleServiceImpl;
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

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        RoleService roleService = RoleServiceImpl.getInstance();
        String name = request.getParameter(ParametersType.NAME.toString());
        String surname = request.getParameter(ParametersType.SURNAME.toString());
        String login = request.getParameter(ParametersType.LOGIN.toString());
        String password = request.getParameter(ParametersType.PASSWORD.toString());
        String confirmPassword = request.getParameter(ParametersType.CONFIRM_PASSWORD.toString());
        Map<String, String> params = new HashMap<>(); // FIXME: 17.04.2022
        params.put(ParametersType.NAME.toString(), name);
        params.put(ParametersType.SURNAME.toString(), surname);
        params.put(ParametersType.LOGIN.toString(), login);
        params.put(ParametersType.PASSWORD.toString(), password);
        params.put(ParametersType.CONFIRM_PASSWORD.toString(), confirmPassword);
        CustomValidator validator = new CustomValidatorImpl();
        try {
            Router route = new Router();
            if (!validator.validateRegistration(params)) {
                route.setPage("pages/registration.jsp"); // FIXME: 15.04.2022 to constant
                route.setType(Router.Type.FORWARD);
                request.setAttribute("register_msg", "Some field(s) is(are) wrong.");
            } else {
                String encryptedPassword = PasswordEncryptor.encrypt(password);
                CustomerEntity customer = CustomerEntity.newBuilder()
                        .setName(name)
                        .setSurname(surname)
                        .setLogin(login)
                        .setPassword(encryptedPassword)
                        .setRole(roleService.getRoleByType(RoleType.CUSTOMER))
                        .build();
                CustomerService customerService = CustomerServiceImpl.getInstance();
                if (!customerService.register(customer)) {
                    route.setPage("pages/registration.jsp"); // FIXME: 15.04.2022 to constant
                    request.setAttribute("register_msg", "Such user is already exists.");
                } else {
                    request.getSession().setAttribute("user", customer);
                    route.setPage("pages/main.jsp");
                }
            }
            return route;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        }
    }
}

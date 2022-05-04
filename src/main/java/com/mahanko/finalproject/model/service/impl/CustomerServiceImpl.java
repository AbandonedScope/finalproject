package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.model.dao.CustomerDao;
import com.mahanko.finalproject.model.dao.impl.CustomerDaoImpl;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.entity.RoleType;
import com.mahanko.finalproject.model.service.CustomerService;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.util.PasswordEncryptor;
import com.mahanko.finalproject.validator.CustomerValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {

    private CustomerValidator validator;

    public CustomerServiceImpl(CustomerValidator validator) {
        this.validator = validator;
    }

    @Override
    public Optional<CustomerEntity> authenticate(String login, String password) throws ServiceException {
        Optional<CustomerEntity> optionalCustomer = Optional.empty();
        if (validator.validateLogin(login)) {
            CustomerDao customerDao = CustomerDaoImpl.getInstance();
            try {
                String encryptedPassword = PasswordEncryptor.encrypt(password);
                optionalCustomer = customerDao.authenticate(login, encryptedPassword);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }

        return optionalCustomer;
    }

    @Override
    public Optional<CustomerEntity> register(RequestParameters parameters) throws ServiceException {
        Optional<CustomerEntity> optionalCustomer = Optional.empty();
        CustomerDao customerDao = CustomerDaoImpl.getInstance();
        String name = parameters.get(ParameterType.USER_NAME);
        String surname = parameters.get(ParameterType.USER_SURNAME);
        String login = parameters.get(ParameterType.USER_LOGIN);
        String password = parameters.get(ParameterType.USER_PASSWORD);
        String confirmPassword = parameters.get(ParameterType.USER_CONFIRM_PASSWORD);

        boolean isValid = true;
        List<String> validationMassages = new ArrayList<>();
        if (!validator.validateName(name)) {
            isValid = false;
            validationMassages.add("Name error");
        }

        if (!validator.validateLogin(login)) {
            isValid = false;
            validationMassages.add("Login error");
        }

        if (!validator.validatePassword(password, confirmPassword)) {
            isValid = false;
            validationMassages.add("Password error");
        }

        if (isValid) {
            String encryptedPassword = PasswordEncryptor.encrypt(password);
            CustomerEntity customer = CustomerEntity.newBuilder()
                    .setName(name)
                    .setSurname(surname)
                    .setLogin(login)
                    .setPassword(encryptedPassword)
                    .setRole(RoleType.CUSTOMER)
                    .build();
            try {
                if (!customerDao.checkExistence(customer.getLogin())) {
                    if (customerDao.insert(customer)) {
                        optionalCustomer = Optional.of(customer);
                    }
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            parameters.put(ParameterType.VALIDATION_MESSAGES, validationMassages);
        }

        return optionalCustomer;
    }
}

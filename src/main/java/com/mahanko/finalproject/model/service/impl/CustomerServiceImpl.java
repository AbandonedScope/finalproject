package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.model.dao.CustomerDao;
import com.mahanko.finalproject.model.dao.impl.CustomerDaoImpl;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.entity.RoleType;
import com.mahanko.finalproject.model.service.CustomerService;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.util.PasswordEncryptor;
import com.mahanko.finalproject.model.validator.CustomerValidator;
import com.mahanko.finalproject.model.validator.impl.CustomerValidatorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mahanko.finalproject.controller.ParameterType.*;
import static com.mahanko.finalproject.controller.ValidationMessage.*;

public class CustomerServiceImpl implements CustomerService {
    private static final CustomerServiceImpl instance = new CustomerServiceImpl();

    private CustomerServiceImpl() {
    }

    public static CustomerServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<CustomerEntity> authenticate(String login, String password) throws ServiceException {
        Optional<CustomerEntity> optionalCustomer = Optional.empty();
        CustomerValidator validator = new CustomerValidatorImpl();

        // FIXME: 11.05.2022
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
        String name = parameters.get(USER_NAME);
        String surname = parameters.get(USER_SURNAME);
        String login = parameters.get(USER_LOGIN);
        String password = parameters.get(USER_PASSWORD);
        String confirmPassword = parameters.get(USER_CONFIRM_PASSWORD);

        boolean isValid = true;
        CustomerValidator validator = new CustomerValidatorImpl();
        List<String> validationMassages = new ArrayList<>();
        if (!validator.validateName(name)) {
            isValid = false;
            validationMassages.add(USERNAME_VALIDATION_MESSAGE);
        }

        if (!validator.validateName(surname)) {
            isValid = false;
            validationMassages.add(USER_SURNAME_VALIDATION_MESSAGE);
        }

        if (!validator.validateLogin(login)) {
            isValid = false;
            validationMassages.add(LOGIN_VALIDATION_MESSAGE);
        }

        if (!validator.validatePassword(password, confirmPassword)) {
            isValid = false;
            validationMassages.add(PASSWORD_VALIDATION_MESSAGE);
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
                if (!customerDao.checkExistence(customer.getLogin())
                        && customerDao.insert(customer)) {
                    optionalCustomer = Optional.of(customer);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            parameters.put(VALIDATION_MESSAGES, validationMassages);
        }

        return optionalCustomer;
    }

    @Override
    public Optional<CustomerEntity> findById(long id) throws ServiceException {
        Optional<CustomerEntity> optionalCustomer = Optional.empty();
        CustomerDao customerDao = CustomerDaoImpl.getInstance();
        try {
            optionalCustomer = customerDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return optionalCustomer;
    }

    @Override
    public void addBonuses(long userId, int bonuses) throws ServiceException {
        CustomerDao customerDao = CustomerDaoImpl.getInstance();
        try {
            Optional<CustomerEntity> optionalCustomer = customerDao.findById(userId);
            if (optionalCustomer.isPresent()) {
                int customerLoyalPoints = optionalCustomer.get().getLoyalPoints();
                customerDao.updateBonuses(userId, customerLoyalPoints + bonuses);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setBlocked(long userId, boolean state) throws ServiceException {
        CustomerDao customerDao = CustomerDaoImpl.getInstance();
        try {
            Optional<CustomerEntity> optionalCustomer = customerDao.findById(userId);
            if (optionalCustomer.isPresent()) {
                CustomerEntity customer = optionalCustomer.get();
                if (state || customer.getRole() != RoleType.ADMIN) {
                    customerDao.updateBlocked(userId, state);
                } // TODO: 29.06.2022
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}

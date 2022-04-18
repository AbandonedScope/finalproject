package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.model.dao.CustomerDao;
import com.mahanko.finalproject.model.dao.impl.CustomerDaoImpl;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.CustomerService;
import com.mahanko.finalproject.util.PasswordEncryptor;
import com.mahanko.finalproject.validator.CustomValidator;
import com.mahanko.finalproject.validator.impl.CustomValidatorImpl;

public class CustomerServiceImpl implements CustomerService {
    private static final CustomerServiceImpl instance = new CustomerServiceImpl();

    private CustomerServiceImpl() {
    }

    public static CustomerServiceImpl getInstance() {
        return instance;
    }

    @Override
    public CustomerEntity authenticate(String login, String password) throws ServiceException {
        CustomValidator validator = new CustomValidatorImpl();
        CustomerEntity customer = null;
        if (validator.validateLogin(login)) {
            CustomerDao customerDao = CustomerDaoImpl.getInstance();
            try {
                String encryptedPassword = PasswordEncryptor.encrypt(password);
                customer = customerDao.authenticate(login, encryptedPassword);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }

        return customer;
    }

    @Override
    public boolean register(CustomerEntity newCustomer) throws ServiceException {
        CustomerDao customerDao = CustomerDaoImpl.getInstance();
        boolean isRegistered = false;
        try {
            if (!customerDao.checkExistence(newCustomer.getLogin())) {
                isRegistered = customerDao.insert(newCustomer);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isRegistered;
    }
}

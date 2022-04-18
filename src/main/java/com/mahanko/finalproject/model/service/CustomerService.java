package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.exception.ServiceException;

public interface CustomerService {
    CustomerEntity authenticate(String login, String password) throws ServiceException;
    boolean register(CustomerEntity newCustomer) throws ServiceException;
}

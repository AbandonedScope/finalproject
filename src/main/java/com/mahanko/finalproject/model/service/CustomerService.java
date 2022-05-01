package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.exception.ServiceException;

import java.util.Optional;

public interface CustomerService {
    Optional<CustomerEntity> authenticate(String login, String password) throws ServiceException;

    Optional<CustomerEntity> register(RequestParameters parameters) throws ServiceException;
}

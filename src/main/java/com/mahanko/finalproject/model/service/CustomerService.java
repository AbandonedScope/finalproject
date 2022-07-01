package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.exception.ServiceException;

import java.util.Optional;

public interface CustomerService {
    Optional<CustomerEntity> authenticate(RequestParameters parameters) throws ServiceException;

    Optional<CustomerEntity> register(RequestParameters parameters) throws ServiceException;

    Optional<CustomerEntity> findById(long id) throws ServiceException;

    void addBonuses(long userId, int bonuses) throws ServiceException;

    void setBlocked(long userId, boolean state) throws ServiceException;
}

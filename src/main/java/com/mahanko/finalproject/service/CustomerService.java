package com.mahanko.finalproject.service;

import com.mahanko.finalproject.exception.ServiceException;

public interface CustomerService {
    boolean authenticate(String login, String password) throws ServiceException;
}

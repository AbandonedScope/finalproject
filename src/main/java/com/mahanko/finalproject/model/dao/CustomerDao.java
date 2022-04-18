package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.exception.DaoException;

public interface CustomerDao extends BaseDao<CustomerEntity> {
    CustomerEntity authenticate(String login, String password) throws DaoException;
    boolean checkExistence(String login) throws DaoException;
}

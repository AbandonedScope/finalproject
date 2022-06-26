package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.exception.DaoException;

import java.util.Optional;

public interface CustomerDao extends BaseDao<CustomerEntity> {
    Optional<CustomerEntity> authenticate(String login, String password) throws DaoException;

    boolean checkExistence(String login) throws DaoException;

    void addBonuses(long userId, int bonuses) throws DaoException;
}

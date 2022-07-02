package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.RoleType;

import java.util.List;
import java.util.Optional;

public interface CustomerDao extends BaseDao<Long, CustomerEntity> {
    Optional<CustomerEntity> authenticate(String login, String password) throws DaoException;

    boolean checkExistence(String login) throws DaoException;

    void updateBonuses(long userId, int bonuses) throws DaoException;

    void updateBlocked(long userId, boolean state) throws DaoException;

    List<CustomerEntity> findByName(String name) throws DaoException;

    void updateRole(long id, RoleType role) throws DaoException;
}

package com.mahanko.finalproject.dao;

import com.mahanko.finalproject.exception.DaoException;

public interface CustomerDao {
    boolean authenticate(String login, String password) throws DaoException;
}

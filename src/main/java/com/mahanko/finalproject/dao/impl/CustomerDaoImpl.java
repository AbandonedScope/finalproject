package com.mahanko.finalproject.dao.impl;

import com.mahanko.finalproject.dao.CustomerDao;
import com.mahanko.finalproject.entity.CustomerEntity;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImpl extends BaseDaoImpl<CustomerEntity> implements CustomerDao {
    private static final String SELECT_PASSWORD_BY_LOGIN = "SELECT password FROM customer WHERE login = ?";
    private static final CustomerDaoImpl instance = new CustomerDaoImpl();

    private CustomerDaoImpl() {
    }

    public static CustomerDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String login, String password) throws DaoException {
        boolean isMatch = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PASSWORD_BY_LOGIN);) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            String passwordFromDb;
            if (resultSet.next()) {
                passwordFromDb = resultSet.getString(1);
                isMatch = password.equals(passwordFromDb);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return isMatch;
    }
}

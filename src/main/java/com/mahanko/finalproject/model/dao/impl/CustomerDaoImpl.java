package com.mahanko.finalproject.model.dao.impl;

import com.mahanko.finalproject.model.dao.CustomerDao;
import com.mahanko.finalproject.model.mapper.CustomRowMapper;
import com.mahanko.finalproject.model.mapper.impl.CustomerRowMapper;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerDaoImpl implements CustomerDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SELECT_BY_PASSWORD_LOGIN =
            "SELECT u_id, u_name, u_surname, u_password, u_login, u_loyaltypoints, u_blocked, u_role " +
            "FROM users " +
            "WHERE u_login = ? AND u_password = ? ";
    private static final String SELECT_BY_ID =
            "SELECT u_id, u_name, u_surname, u_password, u_login, u_loyaltypoints, u_blocked, u_role " +
            "FROM users " +
            "WHERE u_id = ? ";
    private static final String SELECT_EXISTS_BY_LOGIN =
            "SELECT EXISTS (SELECT u_login FROM users WHERE u_login = ?)";
    private static final String INSERT_NEW_CUSTOMER =
            "INSERT INTO users(u_name, u_surname, u_login, u_password, u_loyaltypoints, u_blocked, u_role) " +
                    "VALUE (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_BONUSES =
            "update users set u_loyaltypoints = ? where u_id = ?";
    private static final CustomerDaoImpl instance = new CustomerDaoImpl();

    private CustomerDaoImpl() {
    }

    public static CustomerDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<CustomerEntity> authenticate(String login, String password) throws DaoException {
        Optional<CustomerEntity> optionalCustomer = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_PASSWORD_LOGIN)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                CustomRowMapper<CustomerEntity> mapper = new CustomerRowMapper();
                optionalCustomer = mapper.map(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return optionalCustomer;
    }

    @Override
    public boolean checkExistence(String login) throws DaoException {
        boolean isExist = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_EXISTS_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isExist = resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return isExist;
    }

    @Override
    public void addBonuses(long userId, int bonuses) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_BONUSES)) {
            statement.setInt(1, bonuses);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }


    @Override
    public Optional<CustomerEntity> findById(long id) throws DaoException {
        Optional<CustomerEntity> optionalCustomer = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                CustomRowMapper<CustomerEntity> mapper = new CustomerRowMapper();
                optionalCustomer = mapper.map(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return optionalCustomer;
    }

    @Override
    public boolean insert(CustomerEntity entity) throws DaoException {
        boolean isInserted = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW_CUSTOMER)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getLogin());
            statement.setString(4, entity.getPassword());
            statement.setInt(5, entity.getLoyalPoints());
            statement.setBoolean(6, entity.isBlocked());
            statement.setString(7, entity.getRole().toString());
            if (statement.executeUpdate() != 0) {
                isInserted = true;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return isInserted;
    }

    @Override
    public boolean remove(CustomerEntity customerEntity) throws DaoException {
        return false;
    }

    @Override
    public List<CustomerEntity> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(long id, CustomerEntity customerEntity) throws DaoException {
        return false;
    }
}

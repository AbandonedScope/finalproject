package com.mahanko.finalproject.model.mapper.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.mapper.CustomRowMapper;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.entity.RoleType;
import com.mahanko.finalproject.model.mapper.ColumnName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerRowMapper implements CustomRowMapper<CustomerEntity> {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<CustomerEntity> map(ResultSet resultSet) throws DaoException {
        CustomerEntity customer = new CustomerEntity();
        Optional<CustomerEntity> optionalCustomer = Optional.empty();
        try {
            customer.setId(resultSet.getLong(ColumnName.USER_ID));
            customer.setName(resultSet.getString(ColumnName.USER_NAME));
            customer.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
            customer.setLogin(resultSet.getString(ColumnName.USER_LOGIN));
            customer.setPassword(resultSet.getString(ColumnName.USER_PASSWORD));
            customer.setLoyalPoints(resultSet.getInt(ColumnName.USER_LOYALTY_POINTS));
            customer.setBlocked(resultSet.getBoolean(ColumnName.USER_IS_BLOCKED));
            RoleType roleType = RoleType.valueOf(resultSet.getString(ColumnName.USER_ROLE_TYPE));
            customer.setRole(roleType);
            optionalCustomer = Optional.of(customer);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
        return optionalCustomer;
    }
}

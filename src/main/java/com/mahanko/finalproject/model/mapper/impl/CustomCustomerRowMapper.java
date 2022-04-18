package com.mahanko.finalproject.model.mapper.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.entity.RoleEntity;
import com.mahanko.finalproject.model.entity.RoleType;
import com.mahanko.finalproject.model.mapper.CustomRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CustomCustomerRowMapper implements CustomRowMapper<CustomerEntity> {
    @Override
    public Optional<CustomerEntity> map(ResultSet resultSet) throws DaoException {
        CustomerEntity customer = new CustomerEntity();
        Optional<CustomerEntity> optionalCustomer = Optional.empty();
        try {
            customer.setName(resultSet.getString("u_name"));
            customer.setSurname(resultSet.getString("u_surname"));
            customer.setLogin(resultSet.getString("u_login"));
            customer.setPassword(resultSet.getString("u_password"));
            customer.setLoyalPoints(resultSet.getInt("u_loyaltypoints"));
            customer.setBlocked(resultSet.getBoolean("u_blocked"));
            int roleId = Integer.parseInt(resultSet.getString("u_role"));
            RoleType roleType = RoleType.valueOf(resultSet.getString("r_type"));
            customer.setRole(new RoleEntity(roleId, roleType));
            optionalCustomer = Optional.of(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalCustomer;
    }
}

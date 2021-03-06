package com.mahanko.finalproject.model.mapper.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.entity.PaymentType;
import com.mahanko.finalproject.model.mapper.CustomRowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.mahanko.finalproject.model.mapper.ColumnName.*;

/**
 * The type OrderLiteMapper class. Maps result set to the OrderEntity class object.(maps without menu items).
 */
public class OrderLiteMapper implements CustomRowMapper<OrderEntity> {

    @Override
    public Optional<OrderEntity> map(ResultSet resultSet) throws DaoException {
        Optional<OrderEntity> optionalOrder;
        try {
            long id = resultSet.getLong(ORDER_ID);
            BigDecimal orderedCost = resultSet.getBigDecimal(ORDER_COST);
            Long userId = resultSet.getLong(ORDER_USER_ID);
            String paymentTypeString = resultSet.getString(ORDER_PAYMENT_TYPE);
            LocalDateTime creationDate = resultSet.getTimestamp(ORDER_CREATION_DATE).toLocalDateTime();
            LocalDateTime servingTime = resultSet.getTimestamp(ORDER_SERVING_DATE).toLocalDateTime();
            boolean isTaken = resultSet.getBoolean(ORDER_IS_TAKEN);
            boolean isServed = resultSet.getBoolean(ORDER_IS_SERVED);
            PaymentType paymentType = PaymentType.valueOf(paymentTypeString);
            OrderEntity order = new OrderEntity();
            order.setId(id);
            order.setUserId(userId);
            order.setServingTime(servingTime);
            order.setCreationTime(creationDate);
            order.setTaken(isTaken);
            order.setPaymentType(paymentType);
            order.setOrderedCost(orderedCost);
            order.setServed(isServed);
            optionalOrder = Optional.of(order);
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return optionalOrder;
    }
}

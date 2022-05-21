package com.mahanko.finalproject.model.mapper.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.entity.PaymentType;
import com.mahanko.finalproject.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.mahanko.finalproject.model.mapper.ColumnName.*;


public class OrderMapper implements CustomRowMapper<OrderEntity> {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<OrderEntity> map(ResultSet resultSet) throws DaoException {
        Optional<OrderEntity> optionalOrder;
        try {
            Long id = resultSet.getLong(ORDER_ID);
            BigDecimal cost = resultSet.getBigDecimal(ORDER_COST);
            Long userId = resultSet.getLong(ORDER_USER_ID);
            String paymentTypeString = resultSet.getString(ORDER_PAYMENT_TYPE);
            LocalDateTime creationDate = resultSet.getTimestamp(ORDER_CREATION_DATE).toLocalDateTime();
            LocalDateTime servingTime = resultSet.getTimestamp(ORDER_SERVING_DATE).toLocalDateTime();
            Boolean isTaken = resultSet.getBoolean(ORDER_IS_TAKEN);
            PaymentType paymentType = PaymentType.valueOf(paymentTypeString);
            OrderEntity order = new OrderEntity();
            order.setId(id);
            order.setUserId(userId);
            order.setServingTime(servingTime);
            order.setCreationTime(creationDate);
            order.setTaken(isTaken);
            order.setPaymentType(paymentType);
            optionalOrder = Optional.of(order);
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return optionalOrder;
    }
}

package com.mahanko.finalproject.model.mapper.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.entity.PaymentType;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import com.mahanko.finalproject.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.mahanko.finalproject.model.mapper.ColumnName.*;


public class OrderFullMapper implements CustomRowMapper<OrderEntity> {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<OrderEntity> map(ResultSet resultSet) throws DaoException {
        Optional<OrderEntity> optionalOrder;
        try {
            CustomRowMapper<OrderEntity> liteMapper = new OrderLiteMapper();
            optionalOrder = liteMapper.map(resultSet);
            if (optionalOrder.isPresent()) {
                OrderEntity order = optionalOrder.get();
                CustomRowMapper<MenuItem> menuItemMapper = new MenuItemRowMapper();
                do {
                    Integer count = resultSet.getInt(ORDER_MENU_ITEM_COUNT);
                    menuItemMapper.map(resultSet).ifPresent(item -> order.addItem(item, count));
                } while (resultSet.next() && resultSet.getLong(ORDER_ID) == order.getId());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return optionalOrder;
    }
}

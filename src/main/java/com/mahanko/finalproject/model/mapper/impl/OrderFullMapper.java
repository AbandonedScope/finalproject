package com.mahanko.finalproject.model.mapper.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import com.mahanko.finalproject.model.mapper.CustomRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.mahanko.finalproject.model.mapper.ColumnName.*;


/**
 * The type OrderFullMapper class. Maps result set to the OrderEntity class object.
 */
public class OrderFullMapper implements CustomRowMapper<OrderEntity> {

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

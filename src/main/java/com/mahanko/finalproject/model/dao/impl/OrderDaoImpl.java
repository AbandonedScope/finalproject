package com.mahanko.finalproject.model.dao.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.dao.OrderDao;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import com.mahanko.finalproject.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String INSERT_ORDER = "INSERT INTO orders(OR_COST, or_creation_date, or_serving_date, OR_USER, OR_PAYMENTTYPE) VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_ORDER_MENU_ITEMS_MERGE = "INSERT INTO m2m_orders_menuitems(or_id, mi_id, mi_count) VALUES (?, ?, ?)";
    private static final OrderDaoImpl instance = new OrderDaoImpl();


    private OrderDaoImpl() {
    }

    public static OrderDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<OrderEntity> findById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean insert(OrderEntity orderEntity) throws DaoException {
        boolean isInserted = false;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement mergeStatement = connection.prepareStatement(INSERT_ORDER_MENU_ITEMS_MERGE);) {
            statement.setDouble(1, orderEntity.getCost());
            connection.setAutoCommit(false);
            Timestamp servingTime = Timestamp.valueOf(orderEntity.getServingTime());
            Timestamp creationTime = Timestamp.valueOf(orderEntity.getCreationTime().toLocalDateTime());
            statement.setTimestamp(2, creationTime);
            statement.setTimestamp(3, servingTime);
            statement.setLong(4, orderEntity.getUserId());
            statement.setString(5, "CASH");
            if (statement.executeUpdate() == 1) {
                ResultSet keys = statement.getGeneratedKeys();
                keys.next();
                Long orderId = keys.getLong(1);

                for (var entry : orderEntity.getItems()) {
                    MenuItem meal = entry.getKey();
                    int count = entry.getValue();
                    mergeStatement.setLong(1, orderId);
                    mergeStatement.setLong(2, meal.getId());
                    mergeStatement.setInt(3, count);
                    mergeStatement.executeUpdate();
                }

                isInserted = true;
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                logger.log(Level.ERROR, exception);
                throw new DaoException(exception);
            }
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException exception) {
                logger.log(Level.ERROR, exception);
                throw new DaoException(exception);
            }
        }

        return isInserted;
    }

    @Override
    public boolean remove(OrderEntity orderEntity) throws DaoException {
        return false;
    }

    @Override
    public List<OrderEntity> findAll() throws DaoException {
        return null;
    }

    @Override
    public OrderEntity update(long id, OrderEntity orderEntity) throws DaoException {
        return null;
    }
}

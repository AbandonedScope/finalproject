package com.mahanko.finalproject.model.dao.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.dao.OrderDao;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import com.mahanko.finalproject.model.mapper.CustomRowMapper;
import com.mahanko.finalproject.model.mapper.impl.OrderMapper;
import com.mahanko.finalproject.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SELECT_ALL_LAZY =
            "select or_id, or_cost, or_creation_date, or_serving_date, " +
                    "or_user, or_taken, or_payment_type from orders";
    private static final String INSERT_ORDER =
            "INSERT INTO orders(OR_COST, or_creation_date, or_serving_date, OR_USER, or_payment_type) " +
                    "VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_ORDER_MENU_ITEMS_MERGE =
            "INSERT INTO m2m_orders_menuitems(or_id, mi_id, mi_count) VALUES (?, ?, ?)";
    private static final String SELECT_ORDERS_BY_CUSTOMER_ID =
            "select or_id, or_cost, or_creation_date, or_serving_date, or_user, " +
                    "or_payment_type, or_taken from orders where or_user = ?";
    private static final String SELECT_ALL_OFFSET_LIMIT =
            "select or_id, or_cost, or_creation_date, or_serving_date, " +
                    "or_user, or_taken, or_payment_type from orders limit ?, ?";
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
             PreparedStatement mergeStatement = connection.prepareStatement(INSERT_ORDER_MENU_ITEMS_MERGE)) {
            connection.setAutoCommit(false);
            statement.setBigDecimal(1, orderEntity.getCost());
            Timestamp servingTime = Timestamp.valueOf(orderEntity.getServingTime());
            Timestamp creationTime = Timestamp.valueOf(orderEntity.getCreationTime());
            statement.setTimestamp(2, creationTime);
            statement.setTimestamp(3, servingTime);
            statement.setLong(4, orderEntity.getUserId());
            statement.setString(5, orderEntity.getPaymentType().name());
            if (statement.executeUpdate() == 1) {
                ResultSet keys = statement.getGeneratedKeys();
                keys.next();
                long orderId = keys.getLong(1);

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
            }

            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException exception) {
                logger.log(Level.ERROR, exception);
            }
        }

        return isInserted;
    }

    @Override
    public List<OrderEntity> findByCustomerId(Long id) throws DaoException {
        List<OrderEntity> customerOrders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDERS_BY_CUSTOMER_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                CustomRowMapper<OrderEntity> mapper = new OrderMapper();
                while (resultSet.next()) {
                    mapper.map(resultSet).ifPresent(customerOrders::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return customerOrders;
    }

    @Override
    public List<OrderEntity> findPage(long offset, int pageSize) throws DaoException {
        List<OrderEntity> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_OFFSET_LIMIT)) {
            statement.setLong(1, offset);
            statement.setInt(2, pageSize);
            try (ResultSet resultSet = statement.executeQuery()) {
                CustomRowMapper<OrderEntity> mapper = new OrderMapper();
                while (resultSet.next()) {
                    mapper.map(resultSet).ifPresent(orders::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return orders;
    }

    @Override
    public boolean remove(OrderEntity orderEntity) throws DaoException {
        return false;
    }

    @Override
    public List<OrderEntity> findAll() throws DaoException {
        List<OrderEntity> orderEntities = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_LAZY);
             ResultSet resultSet = statement.executeQuery()) {
            CustomRowMapper<OrderEntity> orderMapper = new OrderMapper();
            while (resultSet.next()) {
                orderMapper.map(resultSet).ifPresent(orderEntities::add);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return orderEntities;
    }

    @Override
    public boolean update(long id, OrderEntity orderEntity) throws DaoException {
        return false;
    }
}

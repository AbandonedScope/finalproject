package com.mahanko.finalproject.model.dao.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.dao.OrderDao;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import com.mahanko.finalproject.model.mapper.CustomRowMapper;
import com.mahanko.finalproject.model.mapper.impl.OrderFullMapper;
import com.mahanko.finalproject.model.mapper.impl.OrderLiteMapper;
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
                    "or_user, or_taken, or_served, or_payment_type from orders ORDER BY or_id DESC";
    private static final String INSERT_ORDER =
            "INSERT INTO orders(OR_COST, or_creation_date, or_serving_date, OR_USER, or_payment_type) " +
                    "VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_ORDER_MENU_ITEMS_MERGE =
            "INSERT INTO m2m_orders_menuitems(or_id, mi_id, mi_count) VALUES (?, ?, ?)";
    private static final String SELECT_ORDERS_BY_CUSTOMER_ID_JOIN_MENU_ITEMS =
            "select orders.or_id, or_cost, or_creation_date, or_serving_date, or_user, " +
                    "or_payment_type, or_taken, m2mom.mi_count, menu_items.mi_id, " +
                    "mi_section, mi_name, mi_picture, mi_cost, or_served, " +
                    "m2mmi.ingr_weight," +
                    "ingredients.ingr_id, ingr_name, ingr_proteins, ingr_fats, ingr_carbohydrates, ingr_calories, ingr_picture " +
                    "from orders " +
                    "join m2m_orders_menuitems m2mom on orders.or_id = m2mom.or_id " +
                    "join menu_items on m2mom.mi_id = menu_items.mi_id " +
                    "join m2m_menuitems_ingredients m2mmi on m2mom.mi_id = m2mmi.mi_id " +
                    "join ingredients on m2mmi.ingr_id = ingredients.ingr_id " +
                    "where or_user = ? ORDER BY orders.or_id DESC";
    private static final String SELECT_ACTIVE_ORDERS_BY_CUSTOMER_ID_JOIN_MENU_ITEMS =
            "select orders.or_id, or_cost, or_creation_date, or_serving_date, or_user, " +
                    "or_payment_type, or_taken, or_served, m2mom.mi_count, " +
                    "menu_items.mi_id, mi_section, mi_name, mi_picture, mi_cost, " +
                    "m2mmi.ingr_weight," +
                    "ingredients.ingr_id, ingr_name, ingr_proteins, ingr_fats, ingr_carbohydrates, ingr_calories, ingr_picture " +
                    "from orders " +
                    "join m2m_orders_menuitems m2mom on orders.or_id = m2mom.or_id " +
                    "join menu_items on m2mom.mi_id = menu_items.mi_id " +
                    "join m2m_menuitems_ingredients m2mmi on m2mom.mi_id = m2mmi.mi_id " +
                    "join ingredients on m2mmi.ingr_id = ingredients.ingr_id " +
                    "where or_user = ? and or_served = 0 ORDER BY orders.or_id DESC";
    private static final String SELECT_ORDER_BY_ID_JOIN_MENU_ITEMS =
            "select orders.or_id, or_cost, or_creation_date, or_serving_date, or_user, " +
                    "or_payment_type, or_taken, m2mom.mi_count, menu_items.mi_id, or_served, " +
                    "mi_section, mi_name, mi_picture, mi_cost, " +
                    "m2mmi.ingr_weight," +
                    "ingredients.ingr_id, ingr_name, ingr_proteins, ingr_fats, ingr_carbohydrates, ingr_calories, ingr_picture " +
                    "from orders " +
                    "join m2m_orders_menuitems m2mom on orders.or_id = m2mom.or_id " +
                    "join menu_items on m2mom.mi_id = menu_items.mi_id " +
                    "join m2m_menuitems_ingredients m2mmi on m2mom.mi_id = m2mmi.mi_id " +
                    "join ingredients on m2mmi.ingr_id = ingredients.ingr_id " +
                    "where orders.or_id = ?";
    private static final String SELECT_ALL_OFFSET_LIMIT =
            "select or_id, or_cost, or_creation_date, or_serving_date, " +
                    "or_user, or_taken, or_served, or_payment_type from orders limit ?, ?";
    private static final String UPDATE_ORDER_TAKEN =
            "update orders set or_taken = ? where or_id = ?";
    private static final String UPDATE_ORDER_SERVED =
            "update orders set or_served = ? where or_id = ?";
    private static final OrderDaoImpl instance = new OrderDaoImpl();


    private OrderDaoImpl() {
    }

    public static OrderDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<OrderEntity> findById(Long id) throws DaoException {
        Optional<OrderEntity> optionalOrder = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_BY_ID_JOIN_MENU_ITEMS)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                CustomRowMapper<OrderEntity> mapper = new OrderFullMapper();
                if (resultSet.next()) {
                    optionalOrder = mapper.map(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return optionalOrder;
    }

    @Override
    public boolean insert(OrderEntity entity) throws DaoException {
        boolean isInserted = false;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement mergeStatement = connection.prepareStatement(INSERT_ORDER_MENU_ITEMS_MERGE)) {
            connection.setAutoCommit(false);
            statement.setBigDecimal(1, entity.getCost());
            Timestamp servingTime = Timestamp.valueOf(entity.getServingTime());
            Timestamp creationTime = Timestamp.valueOf(entity.getCreationTime());
            statement.setTimestamp(2, creationTime);
            statement.setTimestamp(3, servingTime);
            statement.setLong(4, entity.getUserId());
            statement.setString(5, entity.getPaymentType().name());
            if (statement.executeUpdate() == 1) {
                ResultSet keys = statement.getGeneratedKeys();
                keys.next();
                long orderId = keys.getLong(1);

                for (var entry : entity.getItems()) {
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
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDERS_BY_CUSTOMER_ID_JOIN_MENU_ITEMS)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                CustomRowMapper<OrderEntity> mapper = new OrderFullMapper();
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
    public List<OrderEntity> findActiveByCustomerId(Long id) throws DaoException {
        List<OrderEntity> customerOrders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ACTIVE_ORDERS_BY_CUSTOMER_ID_JOIN_MENU_ITEMS)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                CustomRowMapper<OrderEntity> mapper = new OrderFullMapper();
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
                CustomRowMapper<OrderEntity> mapper = new OrderLiteMapper();
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
    public void setTaken(long id, boolean state) throws DaoException {
        try(Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_TAKEN)) {
            statement.setBoolean(1, state);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public void setServed(long id, boolean isServed) throws DaoException {
        try(Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_SERVED)) {
            statement.setBoolean(1, isServed);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<OrderEntity> findAll() throws DaoException {
        List<OrderEntity> orderEntities = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_LAZY);
             ResultSet resultSet = statement.executeQuery()) {
            CustomRowMapper<OrderEntity> orderMapper = new OrderLiteMapper();
            while (resultSet.next()) {
                orderMapper.map(resultSet).ifPresent(orderEntities::add);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return orderEntities;
    }
}

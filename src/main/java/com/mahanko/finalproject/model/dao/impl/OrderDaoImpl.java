package com.mahanko.finalproject.model.dao.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.dao.OrderDao;
import com.mahanko.finalproject.model.entity.OrderEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String INSERT_ORDER = "INSERT INTO orders(OR_COST, OR_DATE, OR_USER, OR_PAYMENTTYPE) VALUES (?, ?, ?, ?)";

    @Override
    public Optional<OrderEntity> findById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean insert(OrderEntity orderEntity) throws DaoException {
        return false;
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

package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.OrderEntity;

import java.util.List;

public interface OrderDao extends FindAllCapable<Long, OrderEntity> {
    List<OrderEntity> findByCustomerId(Long id) throws DaoException;

    List<OrderEntity> findActiveByCustomerId(Long id) throws DaoException;

    List<OrderEntity> findPage(long offset, int pageSize) throws DaoException;

    void setTaken(long id, boolean state) throws DaoException;

    void setServed(long id, boolean state) throws DaoException;
}

package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.OrderEntity;

import java.util.List;

public interface OrderDao extends BaseDao<OrderEntity> {
    List<OrderEntity> findByCustomerId(Long id) throws DaoException;
}

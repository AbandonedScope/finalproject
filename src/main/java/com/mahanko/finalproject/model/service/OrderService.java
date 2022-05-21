package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    List<OrderEntity> findAll() throws ServiceException;

    boolean insertNew(RequestParameters parameters, OrderEntity order) throws ServiceException;

    List<OrderEntity> findOrdersByCustomerId(Long id) throws ServiceException;

    void setItemAmount(OrderEntity order, String itemId, String amount) throws ServiceException;
}

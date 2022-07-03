package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderEntity> findAll() throws ServiceException;

    List<OrderEntity> findActiveByCustomerId(long id) throws ServiceException;

    Optional<OrderEntity> findById(long id) throws ServiceException;

    boolean insertNew(RequestParameters parameters, OrderEntity order) throws ServiceException;

    List<OrderEntity> findOrdersByCustomerId(Long id) throws ServiceException;

    boolean setItemAmount(OrderEntity order, String itemId, String amount) throws ServiceException;

    boolean removeItem(OrderEntity order, String itemId) throws ServiceException;

    void setTaken(long id) throws ServiceException;

    void setServed(long id) throws ServiceException;
}

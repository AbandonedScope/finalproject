package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.OrderEntity;

public interface OrderService {
    boolean insertNew(RequestParameters parameters, OrderEntity order) throws ServiceException;
}

package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.service.OrderService;
import com.mahanko.finalproject.validator.OrderValidator;
import com.mahanko.finalproject.validator.impl.OrderValidatorImpl;

import static com.mahanko.finalproject.controller.ParameterType.ORDER_TIME;
import static com.mahanko.finalproject.controller.ParameterType.USER_ID;

public class OrderServiceImpl implements OrderService {
    @Override
    public boolean insertNew(RequestParameters parameters, OrderEntity order) throws ServiceException {
        String servingTime = parameters.get(ORDER_TIME);
        String userId = parameters.get(USER_ID);
        OrderValidator validator = new OrderValidatorImpl();
        validator.validateServingTime(servingTime);

        return false;
    }
}

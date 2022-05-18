package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.dao.OrderDao;
import com.mahanko.finalproject.model.dao.impl.OrderDaoImpl;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.service.OrderService;
import com.mahanko.finalproject.validator.OrderValidator;
import com.mahanko.finalproject.validator.impl.OrderValidatorImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mahanko.finalproject.controller.ParameterType.*;

public class OrderServiceImpl implements OrderService {
    @Override
    public boolean insertNew(RequestParameters parameters, OrderEntity order) throws ServiceException {
        boolean isInserted = false;
        boolean isValid = true;
        String servingTime = parameters.get(ORDER_TIME);
        String userId = parameters.get(USER_ID);
        LocalDateTime date = LocalDateTime.now();
        ZonedDateTime zoned = LocalDateTime.now().atZone(ZoneId.systemDefault());
        OrderValidator validator = new OrderValidatorImpl();
        List<String> validationMessages = new ArrayList<>();
        if (!validator.validateServingTime(servingTime)) {
            isValid = false;
            validationMessages.add(SERVING_DATETIME_VALIDATION_MESSAGE);
        }

        if (isValid) {
            try {
                order.setCreationTime(zoned);
                order.setServingTime(date);
                order.setUserId(Long.parseLong(userId));
                OrderDao orderDao = OrderDaoImpl.getInstance();
                isInserted = orderDao.insert(order);
            } catch (DaoException e) {
                throw  new ServiceException(e);
            }
        } else {
            parameters.put(VALIDATION_MESSAGES, validationMessages);
        }

        return isInserted;
    }
}

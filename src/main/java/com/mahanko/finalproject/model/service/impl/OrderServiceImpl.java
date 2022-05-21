package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.dao.OrderDao;
import com.mahanko.finalproject.model.dao.impl.OrderDaoImpl;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import com.mahanko.finalproject.model.service.OrderService;
import com.mahanko.finalproject.validator.OrderValidator;
import com.mahanko.finalproject.validator.impl.OrderValidatorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mahanko.finalproject.controller.ParameterType.*;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<OrderEntity> findAll() throws ServiceException {
        List<OrderEntity> orders;
        try {
            OrderDao orderDao = OrderDaoImpl.getInstance();
            orders = orderDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return orders;
    }

    @Override
    public boolean insertNew(RequestParameters parameters, OrderEntity order) throws ServiceException {
        boolean isInserted = false;
        boolean isValid = true;
        String servingDateTimeString = parameters.get(ORDER_TIME);
        String userId = parameters.get(USER_ID);
        OrderValidator validator = new OrderValidatorImpl();
        List<String> validationMessages = new ArrayList<>();
        if (!validator.validateServingTime(servingDateTimeString)) {
            isValid = false;
            validationMessages.add(SERVING_DATETIME_VALIDATION_MESSAGE);
        }

        if (isValid) {
            try {
                LocalDateTime servingDateTime = LocalDateTime.parse(servingDateTimeString);
                order.setCreationTime(LocalDateTime.now());
                order.setServingTime(servingDateTime);
                order.setUserId(Long.parseLong(userId));
                OrderDao orderDao = OrderDaoImpl.getInstance();
                isInserted = orderDao.insert(order);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            parameters.put(VALIDATION_MESSAGES, validationMessages);
        }

        return isInserted;
    }

    @Override
    public List<OrderEntity> findOrdersByCustomerId(Long id) throws ServiceException {
        // TODO: 21.05.2022  
        try {
            OrderDao orderDao = OrderDaoImpl.getInstance();
            orderDao.findByCustomerId(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return null;
    }

    @Override
    public void setItemAmount(OrderEntity order, String itemId, String amount) throws ServiceException {
        // FIXME: 21.05.2022 validation
        long id;
        int itemAmount;
        try {
            id = Long.parseLong(itemId);
            itemAmount = Integer.parseInt(amount);
        } catch (NumberFormatException e) {
            logger.log(Level.ERROR, e);
            throw new ServiceException(e);
        }

        var optionalEntry = order.getItems().stream()
                .filter(entry -> entry.getKey().getId().equals(id))
                .findFirst();
        if (optionalEntry.isEmpty()) {
            logger.log(Level.ERROR, "The was no meal with id {} in order with id {}", itemId, order.getId());
            throw new ServiceException("The was no meal with id " + itemId + " in order with id " + order.getId());
        }

        optionalEntry.get().setValue(itemAmount);
    }
}

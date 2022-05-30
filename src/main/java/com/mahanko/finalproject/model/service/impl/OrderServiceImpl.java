package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.dao.OrderDao;
import com.mahanko.finalproject.model.dao.impl.OrderDaoImpl;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.entity.PaymentType;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import com.mahanko.finalproject.model.service.OrderService;
import com.mahanko.finalproject.model.validator.OrderValidator;
import com.mahanko.finalproject.model.validator.impl.OrderValidatorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        String paymentString = parameters.get(ORDER_PAYMENT_TYPE);
        PaymentType paymentType = PaymentType.define(paymentString);
        OrderValidator validator = new OrderValidatorImpl();
        List<String> validationMessages = new ArrayList<>();
        if (!validator.validateServingTime(servingDateTimeString)) {
            isValid = false;
            validationMessages.add(SERVING_DATETIME_VALIDATION_MESSAGE);
        }

        if (paymentType == null) {
            logger.log(Level.ERROR, "There is no such payment type '{}'", paymentString);
            throw new ServiceException("There is no such payment type '" + paymentString + "'");
        }

        if (isValid) {
            try {
                LocalDateTime servingDateTime = LocalDateTime.parse(servingDateTimeString);
                order.setCreationTime(LocalDateTime.now());
                order.setServingTime(servingDateTime);
                order.setUserId(Long.parseLong(userId));
                order.setPaymentType(paymentType);
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
        List<OrderEntity> orders;
        try {
            OrderDao orderDao = OrderDaoImpl.getInstance();
            orders = orderDao.findByCustomerId(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return orders;
    }

    @Override
    public boolean setItemAmount(OrderEntity order, String itemId, String amount) throws ServiceException {
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

        OrderValidator validator = new OrderValidatorImpl();
        boolean isValid = validator.validateItemAmount(itemAmount);

        if (isValid) {
            var orderEntry = getOrderItemAmountPairById(order, id);
            orderEntry.setValue(itemAmount);
        }

        return isValid;
    }

    @Override
    public boolean removeItem(OrderEntity order, String itemId) throws ServiceException {
        if (order == null) {
            logger.log(Level.ERROR, "Order was null");
            throw new ServiceException("Order was null");
        }

        long menuItemId;
        try {
            menuItemId = Long.parseLong(itemId);
        } catch (NumberFormatException e) {
            logger.log(Level.ERROR, e);
            throw  new ServiceException(e);
        }

        var orderEntry = getOrderItemAmountPairById(order, menuItemId);
        return order.removeItem(orderEntry.getKey()) != null;
    }

    private Map.Entry<MenuItem, Integer> getOrderItemAmountPairById(OrderEntity order, long itemId) throws ServiceException {
        var optionalEntry = order.getItems().stream()
                .filter(entry -> entry.getKey().getId().equals(itemId))
                .findFirst();
        if (optionalEntry.isEmpty()) {
            logger.log(Level.ERROR, "The was no meal with id {} in order with id {}", itemId, order.getId());
            throw new ServiceException("The was no meal with id " + itemId + " in order with id " + order.getId());
        }

        return optionalEntry.get();
    }
}

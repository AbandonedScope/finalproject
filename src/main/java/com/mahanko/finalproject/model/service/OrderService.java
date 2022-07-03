package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

/**
 * The interface MenuSectionService.
 */
public interface OrderService {
    /**
     * Find all orders.
     *
     * @return the list of all orders.
     * @throws ServiceException the service exception.
     */
    List<OrderEntity> findAll() throws ServiceException;

    /**
     * Find all customer orders which served states are {@code false}.
     *
     * @param id the id of the customer to find orders by.
     * @return The list of founded customer active orders.
     * @throws ServiceException the service exception.
     */
    List<OrderEntity> findActiveByCustomerId(long id) throws ServiceException;

    /**
     * Find order by its id.
     *
     * @param id the id of the order to find.
     * @return If order was found return order optional, otherwise empty optional.
     * @throws ServiceException the service exception.
     */
    Optional<OrderEntity> findById(long id) throws ServiceException;

    /**
     * Insert new order ino database.
     *
     * @param parameters the parameters, must contain order serving date and time, customer id, payment type.
     * @param order      the order to insert into database with its menu items and its amounts.
     * @return true? if order was inserted.
     * @throws ServiceException the service exception.
     */
    boolean insert(RequestParameters parameters, OrderEntity order) throws ServiceException;

    /**
     * Find order by customer id.
     *
     * @param id the id of the customer to find orders by.
     * @return The list of founded customer orders.
     * @throws ServiceException the service exception.
     */
    List<OrderEntity> findOrdersByCustomerId(Long id) throws ServiceException;

    /**
     * Set amount of the certain menu item in the order to the given.
     *
     * @param order  the order.
     * @param itemId the menu item id.
     * @param amount the menu item amount.
     * @return {@code true}, if item amount was set.
     * @throws ServiceException the service exception.
     */
    boolean setItemAmount(OrderEntity order, String itemId, String amount) throws ServiceException;

    /**
     * Remove menu item from the order.
     *
     * @param order  the order.
     * @param itemId the menu item id.
     * @return {@code true}, if menu item was removed.
     * @throws ServiceException the service exception.
     */
    boolean removeItem(OrderEntity order, String itemId) throws ServiceException;

    /**
     * Set order taken state to true.
     *
     * @param id the order id.
     * @throws ServiceException the service exception.
     */
    void setTaken(long id) throws ServiceException;

    /**
     * Set order served state to true.
     *
     * @param id the order id.
     * @throws ServiceException the service exception.
     */
    void setServed(long id) throws ServiceException;
}

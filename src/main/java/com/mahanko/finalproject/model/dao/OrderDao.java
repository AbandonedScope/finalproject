package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.OrderEntity;

import java.util.List;

/**
 * The interface Order dao.
 */
public interface OrderDao extends FindAllCapable<Long, OrderEntity> {
    /**
     * Find list of all orders by customer id.
     * @param id the id of the customer to find orders by.
     * @return The list of the founded orders.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    List<OrderEntity> findByCustomerId(Long id) throws DaoException;

    /**
     * Find list of not served orders by customer id.
     * @param id the id of the customer to find orders by.
     * @return The list of the founded orders.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    List<OrderEntity> findActiveByCustomerId(Long id) throws DaoException;

    /**
     * Update order taken state to the given.
     * @param id the id of the order to update taken state to.
     * @param state the new taken state.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    void setTaken(long id, boolean state) throws DaoException;

    /**
     * Update order served state to the given.
     * @param id the id of the order to update served state to.
     * @param state the new served state.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    void setServed(long id, boolean state) throws DaoException;
}

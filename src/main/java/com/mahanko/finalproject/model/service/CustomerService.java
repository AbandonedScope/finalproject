package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.RoleType;

import java.util.List;
import java.util.Optional;

/**
 * The interface CustomerService.
 */
public interface CustomerService {
    /**
     * Authenticate the customer using its login and password.
     *
     * @param parameters the parameters, that must contains customer login and password.
     * @return If user was authenticated return customer optional, otherwise empty optional.
     * @throws ServiceException the service exception.
     */
    Optional<CustomerEntity> authenticate(RequestParameters parameters) throws ServiceException;

    /**
     * Add new customer into database and return it.
     *
     * @param parameters the parameters, that must contains customer name, surname, login, password and confirm password.
     * @return If user was registered return customer optional, otherwise empty optional.
     * @throws ServiceException the service exception.
     */
    Optional<CustomerEntity> register(RequestParameters parameters) throws ServiceException;

    /**
     * Find customer by id.
     *
     * @param id the id of the customer to find by.
     * @return If customer was found return customer optional, otherwise empty optional.
     * @throws ServiceException the service exception.
     */
    Optional<CustomerEntity> findById(long id) throws ServiceException;

    /**
     * Add certain amount of loyalty points to customers loyalty points.
     *
     * @param userId  the id of the customer to add points to.
     * @param bonuses the amount of loyalty points. Can be negative.
     * @throws ServiceException the service exception.
     */
    void addLoyaltyPoints(long userId, int bonuses) throws ServiceException;

    /**
     * Set customer blocked state to given one.
     *
     * @param userId the id of the customer to set blocked state to.
     * @param state  the blocked state.
     * @throws ServiceException the service exception.
     */
    void setBlocked(long userId, boolean state) throws ServiceException;

    /**
     * Find list of the customers by name, surname and login.
     *
     * @param name the name, surname or login to find by.
     * @return to list of the founded customers.
     * @throws ServiceException the service exception.
     */
    List<CustomerEntity> findByName(String name) throws ServiceException;

    /**
     * Set user role to givaen.
     *
     * @param id   the id of the user to set role to.
     * @param role the role to set.
     * @throws ServiceException the service exception.
     */
    void setRole(long id, RoleType role) throws ServiceException;
}

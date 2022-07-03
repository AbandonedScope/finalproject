package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.RoleType;

import java.util.List;
import java.util.Optional;

/**
 * The interface Customer dao.
 */
public interface CustomerDao extends FindInsertCapable<Long, CustomerEntity> {
    /**
     *
     * @param login the login used to identify user.
     * @param password the password used for authenticate user.
     * @return the optional user
     * @throws DaoException - if the was any SQLException during database operations.
     */
    Optional<CustomerEntity> authenticate(String login, String password) throws DaoException;

    /**
     * Check the existence uf user with given login.
     * @param login the user login
     * @return The boolean. true - if user with such login exists, otherwise false.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    boolean checkExistence(String login) throws DaoException;

    /**
     * Update user loyal points to the given.
     * @param userId the id of the user to update loyal point to.
     * @param bonuses the new loyal point amount.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    void updateLoyalPoints(long userId, int bonuses) throws DaoException;

    /**
     * Update user blocked status to the given.
     * @param userId the id of the user to update block status to.
     * @param state the new blocked status.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    void updateBlocked(long userId, boolean state) throws DaoException;

    /**
     * Find users by name, surname and login.
     * @param name the name, surname or login to find for.
     * @return the list of users with names, surnames and logins containing the giving name.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    List<CustomerEntity> findByName(String name) throws DaoException;

    /**
     * Update user role to given.
     * @param id the id of the user to update role to.
     * @param role the new role to update user old role to.
     * @throws DaoException - if the was any SQLException during database operations.
     */
    void updateRole(long id, RoleType role) throws DaoException;
}

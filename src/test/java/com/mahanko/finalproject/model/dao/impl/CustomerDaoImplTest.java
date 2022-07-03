package com.mahanko.finalproject.model.dao.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.entity.RoleType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class CustomerDaoImplTest extends AbstractDaoTest {
    private static CustomerEntity customer;
    private static final long customerId = 1;
    private static final String customerName = "Maxim";
    private static final String customerSurname = "Mahanko";
    private static final String customerLogin= "maximbo";
    private static final String customerPassword= "123456789";
    private static final RoleType customerRole = RoleType.CUSTOMER;
    private static final CustomerDaoImpl customerDao = CustomerDaoImpl.getInstance();

    @BeforeMethod
    public void setUp() {
        customer = CustomerEntity.newBuilder()
                .setId(customerId)
                .setName(customerName)
                .setSurname(customerSurname)
                .setLogin(customerLogin)
                .setPassword(customerPassword)
                .setRole(customerRole)
                .build();
    }

    @Test(priority = 0)
    public void testInsert() throws DaoException {
        assertTrue(customerDao.insert(customer));
    }

    @Test(priority = 1, dependsOnMethods = "testInsert")
    public void testAuthenticate() throws DaoException {
        var actual = customerDao.authenticate(customerLogin, customerPassword).get();
        assertEquals(actual, customer);
    }

    @Test(priority = 1, dependsOnMethods = "testInsert")
    public void testCheckExistence() throws DaoException {
        assertTrue(customerDao.checkExistence(customerLogin));
    }

    @Test(priority = 1, dependsOnMethods = "testInsert")
    public void testFindByName() throws DaoException {
        var actual = customerDao.findByName(customerName);
        assertEquals(actual, List.of(customer));
    }

    @Test(priority = 1, dependsOnMethods = "testInsert")
    public void testFindById() throws DaoException {
        var actual = customerDao.findById(customerId).get();
        assertEquals(actual, customer);
    }

    @Test(priority = 2, dependsOnMethods = "testFindById")
    public void testUpdateBonuses() throws DaoException {
        int newBonuses = 15;
        customerDao.updateLoyalPoints(customerId, newBonuses);
        var updatedCustomer = customerDao.findById(customerId).get();
        assertEquals(updatedCustomer.getLoyaltyPoints(), newBonuses);
    }

    @Test(priority = 2, dependsOnMethods = "testFindById")
    public void testUpdateBlocked() throws DaoException {
        boolean blocked = true;
        customerDao.updateBlocked(customerId, blocked);
        var updatedCustomer = customerDao.findById(customerId).get();
        assertTrue(updatedCustomer.isBlocked());
    }

    @Test(priority = 2, dependsOnMethods = "testFindById")
    public void testUpdateRole() throws DaoException {
        RoleType newRole = RoleType.ADMIN;
        customerDao.updateRole(customerId, newRole);
        var updatedCustomer = customerDao.findById(customerId).get();
        assertEquals(updatedCustomer.getRole(), newRole);
    }

    @Test
    public void testRemove() throws DaoException {
    }

    @Test
    public void testFindAll() {
    }

    @Test
    public void testUpdate() {
    }
}
package com.mahanko.finalproject.model.dao.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.MenuSection;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class MenuSectionDaoImplTest extends AbstractDaoTest {
    private final MenuSection section;
    private final String sectionName = "Section";
    private int sectionId = 1;


    public MenuSectionDaoImplTest() {
        section = new MenuSection();
        section.setName(sectionName);
    }

    @Test(priority = 0)
    public void testInsert() throws DaoException {
        boolean actual = MenuSectionDaoImpl.getInstance().insert(section);
        assertTrue(actual);
    }

    @Test(priority = 1, dependsOnMethods = "testInsert")
    public void testFindById() throws DaoException {
        MenuSection expected = section;
        MenuSection actual = MenuSectionDaoImpl.getInstance().findById(sectionId).get();
        assertEquals(actual, expected);
    }

    @Test(priority = 2, dependsOnMethods = "testInsert")
    public void testFindByName() throws DaoException {
        var expected = List.of(section);
        List<MenuSection> actual = MenuSectionDaoImpl.getInstance().findByName(sectionName);
        assertEquals(actual, expected);
    }

    @Test(priority = 3, dependsOnMethods = "testInsert")
    public void testFindAll() throws DaoException {
        var expected = List.of(section);
        List<MenuSection> actual = MenuSectionDaoImpl.getInstance().findAll();
        assertEquals(actual, expected);
    }

    @Test(priority = 3)
    public void testExistsMerge()  throws DaoException {
        assertFalse(MenuSectionDaoImpl.getInstance().existsMerge(sectionId));
    }

    @Test(priority = 4, dependsOnMethods = {"testInsert", "testFindByName"})
    public void testSetHiddenTrue() throws DaoException {
        MenuSectionDaoImpl.getInstance().updateHidden(sectionId, true);
        List<MenuSection> actual = MenuSectionDaoImpl.getInstance().findByName(sectionName);
        assertEquals(actual, List.of());
    }

    @Test(priority = 4, dependsOnMethods = {"testInsert", "testFindByName"})
    public void testSetHiddenFalse() throws DaoException {
        MenuSectionDaoImpl.getInstance().updateHidden(sectionId, false);
        List<MenuSection> actual = MenuSectionDaoImpl.getInstance().findByName(sectionName);
        assertEquals(actual, List.of(section));
    }

    @Test(priority = 5, dependsOnMethods = {"testInsert", "testFindById"})
    public void testUpdate() throws DaoException {
        var updatedSection = new MenuSection();
        String updatedName = "Updated section";
        updatedSection.setName(updatedName);
        updatedSection.setId(sectionId);
        MenuSectionDaoImpl menuSectionDao = MenuSectionDaoImpl.getInstance();
        menuSectionDao.update(sectionId, updatedSection);
        Optional<MenuSection> optionalSection = menuSectionDao.findById(sectionId);
        MenuSection actual = optionalSection.get();
        assertEquals(actual, updatedSection);
    }

    @Test(priority = 6, dependsOnMethods = {"testInsert", "testFindById"})
    public void testRemove() throws DaoException {
        MenuSectionDaoImpl.getInstance().insert(section);
        int sectionId = section.getId();
        MenuSectionDaoImpl menuSectionDao = MenuSectionDaoImpl.getInstance();
        menuSectionDao.remove(sectionId);
        Optional<MenuSection> removedSection = menuSectionDao.findById(sectionId);
        assertTrue(removedSection.isEmpty());
    }
}
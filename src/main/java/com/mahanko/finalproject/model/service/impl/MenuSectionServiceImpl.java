package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.dao.MenuItemDao;
import com.mahanko.finalproject.model.dao.impl.MenuItemDaoImpl;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import com.mahanko.finalproject.model.entity.menu.MenuSection;
import com.mahanko.finalproject.model.dao.MenuSectionDao;
import com.mahanko.finalproject.model.dao.impl.MenuSectionDaoImpl;
import com.mahanko.finalproject.model.service.MenuSectionService;
import com.mahanko.finalproject.model.validator.MenuSectionValidator;
import com.mahanko.finalproject.model.validator.impl.MenuSectionValidatorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mahanko.finalproject.controller.ParameterType.*;
import static com.mahanko.finalproject.controller.ValidationMessage.SECTION_NAME_VALIDATION_MESSAGE;
import static com.mahanko.finalproject.controller.ValidationMessage.VALIDATION_MESSAGES;

/**
 * The type MenuSectionServiceImpl class. Performs operations with menu sections. Singleton.
 */
public class MenuSectionServiceImpl implements MenuSectionService {
    private static final Logger logger = LogManager.getLogger();
    private static final MenuSectionServiceImpl instance = new MenuSectionServiceImpl();

    private MenuSectionServiceImpl() {
    }

    /**
     * Get instance of the MenuSectionServiceImpl class.
     *
     * @return the instance
     */
    public static MenuSectionServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<MenuSection> findAll() throws ServiceException {
        MenuSectionDao menuSectionDao = MenuSectionDaoImpl.getInstance();
        MenuItemDao menuItemDao = MenuItemDaoImpl.getInstance();
        List<MenuSection> sections;
        try {
            sections = menuSectionDao.findAll();
            for (var section : sections) {
                List<MenuItem> sectionItems = menuItemDao.findBySectionId(section.getId());
                section.addItems(sectionItems);
            }

            sections = sections.stream()
                    .filter(section -> section.itemsCount() > 0)
                    .collect(Collectors.toList());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return sections;
    }

    @Override
    public List<MenuSection> findByName(String name) throws ServiceException {
        MenuSectionDao menuSectionDao = MenuSectionDaoImpl.getInstance();
        List<MenuSection> sections;
        try {
            sections = menuSectionDao.findByName(name);
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
            throw new ServiceException(e);
        }
        return sections;
    }

    @Override
    public List<MenuSection> findAllLazy() throws ServiceException {
        MenuSectionDao menuSectionDao = MenuSectionDaoImpl.getInstance();
        List<MenuSection> sections;
        try {
            sections = menuSectionDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.ERROR, e);
            throw new ServiceException(e);
        }
        return sections;
    }

    @Override
    public boolean insert(RequestParameters params) throws ServiceException {
        boolean isInserted = false;
        boolean isValid = true;
        String sectionName = params.get(MENU_SECTION_NAME).trim();
        MenuSectionValidator validator = new MenuSectionValidatorImpl();
        List<String> validationMessages = new ArrayList<>();
        if (!validator.validateName(sectionName)) {
            isValid = false;
            validationMessages.add(SECTION_NAME_VALIDATION_MESSAGE);
        }

        if (isValid) {
            try {
                MenuSectionDao menuSectionDao = MenuSectionDaoImpl.getInstance();
                MenuSection section = new MenuSection();
                section.setName(sectionName);
                isInserted = menuSectionDao.insert(section);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            params.put(VALIDATION_MESSAGES, validationMessages);
        }

        return isInserted;
    }

    @Override
    public boolean update(int id, RequestParameters parameters) throws ServiceException {
        boolean updated = false;
        String sectionIdString = parameters.get(MENU_SECTION_ID);
        String sectionName = parameters.get(MENU_SECTION_NAME);
        int sectionId = Integer.parseInt(sectionIdString);
        MenuSection section = new MenuSection();
        section.setId(sectionId);
        MenuSectionValidator validator = new MenuSectionValidatorImpl();
        if (validator.validateName(sectionName)) {
            section.setName(sectionName);
            MenuSectionDao menuSectionDao = MenuSectionDaoImpl.getInstance();
            try {
                updated = menuSectionDao.update(sectionId, section);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            parameters.put(VALIDATION_MESSAGES, List.of(SECTION_NAME_VALIDATION_MESSAGE));
        }

        return updated;
    }

    @Override
    public boolean remove(int id) throws ServiceException {
        boolean removed;
        try {
            MenuSectionDao menuSectionDao = MenuSectionDaoImpl.getInstance();
            if (menuSectionDao.existsMerge(id)) {
                removed = menuSectionDao.updateHidden(id, true);
            } else {
                removed = menuSectionDao.remove(id);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return removed;
    }
}

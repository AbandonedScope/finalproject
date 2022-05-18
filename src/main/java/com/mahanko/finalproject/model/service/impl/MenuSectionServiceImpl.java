package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.controller.RequestParameters;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.MenuSection;
import com.mahanko.finalproject.model.dao.MenuSectionDao;
import com.mahanko.finalproject.model.dao.impl.MenuSectionDaoImpl;
import com.mahanko.finalproject.model.service.MenuSectionService;
import com.mahanko.finalproject.validator.MenuSectionValidator;
import com.mahanko.finalproject.validator.impl.MenuSectionValidatorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.mahanko.finalproject.controller.ParameterType.*;
import static com.mahanko.finalproject.controller.ParameterType.VALIDATION_MESSAGES;

public class MenuSectionServiceImpl implements MenuSectionService {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<MenuSection> findAll() throws ServiceException {
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
        String sectionName = params.get(MENU_SECTION_NAME);
        MenuSectionValidator validator = new MenuSectionValidatorImpl();
        List<String> validationMessages = new ArrayList<>();
        if (!validator.validateName(sectionName)) {
            isValid = false;
            validationMessages.add(SECTION_NAME_VALIDATION_MESSAGE);
        }

        // FIXME: 04.05.2022
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
}

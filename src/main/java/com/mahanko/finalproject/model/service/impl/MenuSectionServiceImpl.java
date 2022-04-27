package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.dao.MenuSectionDao;
import com.mahanko.finalproject.model.dao.impl.MenuSectionDaoImpl;
import com.mahanko.finalproject.model.entity.menu.MenuSection;
import com.mahanko.finalproject.model.service.MenuSectionService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

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
}

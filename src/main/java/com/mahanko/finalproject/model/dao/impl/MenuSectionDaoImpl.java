package com.mahanko.finalproject.model.dao.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.dao.MenuSectionDao;
import com.mahanko.finalproject.model.entity.menu.MenuSection;
import com.mahanko.finalproject.model.mapper.CustomRowMapper;
import com.mahanko.finalproject.model.mapper.impl.MenuSectionRowMapper;
import com.mahanko.finalproject.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuSectionDaoImpl implements MenuSectionDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SELECT_ALL_SECTIONS = "SELECT s_id, s_name FROM sections";
    private static final String SELECT_SECTION_BY_ID = "SELECT s_id, s_name FROM sections WHERE s_id = ?";
    private static final MenuSectionDaoImpl instance = new MenuSectionDaoImpl();


    private MenuSectionDaoImpl() {
    }

    public static MenuSectionDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<MenuSection> findById(Long id) throws DaoException {
        Optional<MenuSection> sectionOptional = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_SECTION_BY_ID)) {
            statement.setInt(1, id.intValue());
            ResultSet resultSet = statement.executeQuery();;
            if (resultSet.next()) {
                CustomRowMapper<MenuSection> mapper = new MenuSectionRowMapper();
                sectionOptional = mapper.map(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
        return sectionOptional;
    }

    @Override
    public boolean insert(MenuSection menuSection) throws DaoException {
        return false;
    }

    @Override
    public boolean remove(MenuSection menuSection) throws DaoException {
        return false;
    }

    @Override
    public List<MenuSection> findAll() throws DaoException {
        List<MenuSection> sections = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SECTIONS)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            MenuSectionRowMapper mapper = new MenuSectionRowMapper();
            while (!resultSet.isAfterLast()) {
                // FIXME: 27.04.2022 if empty???
                mapper.map(resultSet).ifPresent(sections::add);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return sections;
    }

    @Override
    public MenuSection update(long id, MenuSection menuSection) throws DaoException {
        return null;
    }
}
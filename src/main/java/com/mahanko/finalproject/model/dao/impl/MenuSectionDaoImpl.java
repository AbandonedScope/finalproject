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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuSectionDaoImpl implements MenuSectionDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SELECT_ALL_SECTIONS =
            "SELECT s_id, s_name FROM sections";
    private static final String SELECT_SECTION_BY_ID =
            "SELECT s_id, s_name FROM sections WHERE s_id = ?";
    private static final String INSERT_SECTION =
            "INSERT INTO sections(s_name) VALUES (?)";
    private static final String SELECT_BY_NAME =
            "SELECT s_id, s_name FROM sections WHERE locate(?, s_name) > 0 and s_hidden = 0";
    private static final String UPDATE_SECTION_BY_ID =
            "update sections set s_id = ?, s_name = ? where s_id = ?";
    private static final String SELECT_EXISTS_IN_MENU_ITEMS_BY_ID =
            "select mi_section from menu_items where mi_section = ? limit 1";
    private static final String REMOVE_BY_ID =
            "delete from sections where s_id = ?";
    private static final String UPDATE_HIDDEN_BY_ID =
            "update sections set s_hidden = ? where s_id = ?";
    private static final MenuSectionDaoImpl instance = new MenuSectionDaoImpl();


    private MenuSectionDaoImpl() {
    }

    public static MenuSectionDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<MenuSection> findById(long id) throws DaoException {
        Optional<MenuSection> sectionOptional = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_SECTION_BY_ID)) {
            statement.setInt(1, (int) id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    CustomRowMapper<MenuSection> mapper = new MenuSectionRowMapper();
                    sectionOptional = mapper.map(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return sectionOptional;
    }

    @Override
    public boolean insert(MenuSection entity) throws DaoException {
        boolean isInserted = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SECTION, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            if (statement.executeUpdate() != 0) {
                try (ResultSet keysSet = statement.getGeneratedKeys()) {
                    keysSet.next();
                    entity.setId(keysSet.getInt(1));
                }
                isInserted = true;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return isInserted;
    }

    @Override
    public boolean remove(Integer id) throws DaoException {
        boolean removed = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_BY_ID)) {
            statement.setLong(1, id);
            if (statement.executeUpdate() == 1) {
                removed = true;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return removed;
    }

    @Override
    public List<MenuSection> findAll() throws DaoException {
        List<MenuSection> sections = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SECTIONS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                MenuSectionRowMapper mapper = new MenuSectionRowMapper();
                while (!resultSet.isAfterLast()) {
                    mapper.map(resultSet).ifPresent(sections::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return sections;
    }

    @Override
    public boolean update(long id, MenuSection entity) throws DaoException {
        boolean isInserted = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SECTION_BY_ID)) {
            statement.setInt(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.setInt(3, (int) id);
            if (statement.executeUpdate() == 1) {
                isInserted = true;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return isInserted;
    }

    @Override
    public List<MenuSection> findByName(String name) throws DaoException {
        List<MenuSection> sections = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                CustomRowMapper<MenuSection> mapper = new MenuSectionRowMapper();
                while (resultSet.next()) {
                    mapper.map(resultSet).ifPresent(sections::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return sections;
    }

    @Override
    public boolean existsMerge(long sectionId) throws DaoException {
        boolean exists = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_EXISTS_IN_MENU_ITEMS_BY_ID)) {
            statement.setLong(1, sectionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    exists = true;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return exists;
    }

    @Override
    public boolean updateHidden(Integer id, boolean state) throws DaoException {
        boolean updated = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_HIDDEN_BY_ID)) {
            statement.setBoolean(1, state);
            statement.setLong(2, id);
            if (statement.executeUpdate() == 1) {
                updated = true;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return updated;
    }
}

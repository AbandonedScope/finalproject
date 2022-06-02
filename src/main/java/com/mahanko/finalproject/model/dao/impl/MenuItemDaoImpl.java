package com.mahanko.finalproject.model.dao.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.Ingredient;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import com.mahanko.finalproject.model.dao.MenuItemDao;
import com.mahanko.finalproject.model.mapper.CustomRowMapper;
import com.mahanko.finalproject.model.mapper.impl.MenuItemRowMapper;
import com.mahanko.finalproject.model.pool.ConnectionPool;
import com.mahanko.finalproject.util.CustomPictureEncoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuItemDaoImpl implements MenuItemDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SELECT_BY_ID =
            "SELECT menu_items.mi_id, mi_section, mi_name, mi_picture, mi_cost, " +
                    "m2m_menuitems_ingredients.ingr_weight," +
                    "ingredients.ingr_id, ingr_name, ingr_proteins, ingr_fats, ingr_carbohydrates, ingr_calories, ingr_picture " +
                    "FROM menu_items " +
                    "INNER JOIN m2m_menuitems_ingredients " +
                    "ON menu_items.mi_id = m2m_menuitems_ingredients.mi_id " +
                    "INNER JOIN ingredients " +
                    "ON ingredients.ingr_id = m2m_menuitems_ingredients.ingr_id " +
                    "WHERE menu_items.mi_id = ?";
    private static final String INSERT_NEW_MENU_ITEM =
            "INSERT INTO menu_items(mi_section, mi_name, mi_picture, mi_cost) " +
                    "VALUE (?, ?, ?, ?)";
    private static final String SELECT_ID_BY_NAME = "SELECT mi_id FROM menu_items WHERE mi_name = ?";
    private static final String DELETE_MENU_ITEM_INGREDIENTS_MERGE = "delete from m2m_menuitems_ingredients where mi_id = ?";
    private static final String INSERT_MENU_ITEM_INGREDIENTS_MERGE =
            "INSERT INTO m2m_menuitems_ingredients(mi_id, ingr_id, ingr_weight) " +
                    "VALUE (?, ?, ?)";
    private static final String SELECT_ALL_MENU_ITEMS_JOIN_INGREDIENTS =
            "SELECT menu_items.mi_id, mi_section, mi_name, mi_picture, mi_cost, " +
                    "m2m_menuitems_ingredients.ingr_weight," +
                    "ingredients.ingr_id, ingr_name, ingr_proteins, ingr_fats, ingr_carbohydrates, ingr_calories, ingr_picture " +
                    "FROM menu_items " +
                    "INNER JOIN m2m_menuitems_ingredients " +
                    "ON menu_items.mi_id = m2m_menuitems_ingredients.mi_id " +
                    "INNER JOIN ingredients " +
                    "ON ingredients.ingr_id = m2m_menuitems_ingredients.ingr_id";
    private static final String SELECT_ALL_JOIN_INGREDIENTS_BY_SECTION_ID =
            "select menu_items.mi_id, mi_section, mi_name, mi_picture, mi_cost, " +
            "m2m_menuitems_ingredients.ingr_weight, " +
            "ingredients.ingr_id, ingr_name, ingr_proteins, ingr_fats, ingr_carbohydrates, ingr_calories, ingr_picture " +
            "FROM menu_items " +
            "INNER JOIN m2m_menuitems_ingredients " +
            "ON menu_items.mi_id = m2m_menuitems_ingredients.mi_id " +
            "INNER JOIN ingredients " +
            "ON ingredients.ingr_id = m2m_menuitems_ingredients.ingr_id " +
            "where mi_section = ?";
    private static final String SELECT_MENU_ITEMS_BY_NAME =
            "SELECT menu_items.mi_id, mi_section, mi_name, mi_picture, mi_cost, " +
                    "m2m_menuitems_ingredients.ingr_weight," +
                    "ingredients.ingr_id, ingr_name, ingr_proteins, ingr_fats, ingr_carbohydrates, ingr_calories, ingr_picture " +
                    "FROM menu_items " +
                    "INNER JOIN m2m_menuitems_ingredients " +
                    "ON menu_items.mi_id = m2m_menuitems_ingredients.mi_id " +
                    "INNER JOIN ingredients " +
                    "ON ingredients.ingr_id = m2m_menuitems_ingredients.ingr_id " +
                    "WHERE locate(?, mi_name) > 0";
    private static final String UPDATE_MENU_ITEM_BY_ID = "update menu_items set mi_id = ?, mi_name = ?, mi_picture = ?, mi_cost = ?, mi_section = ? where mi_id = ?";
    private static final MenuItemDaoImpl instance = new MenuItemDaoImpl();

    private MenuItemDaoImpl() {
    }

    public static MenuItemDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<MenuItem> findById(Long id) throws DaoException {
        Optional<MenuItem> item = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                MenuItemRowMapper mapper = new MenuItemRowMapper();
                item = mapper.map(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return item;
    }

    @Override
    public boolean insert(MenuItem menuItem) throws DaoException {
        boolean isInserted = false;
        // FIXME: 22.04.2022 transactions
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW_MENU_ITEM);
             PreparedStatement idStatement = connection.prepareStatement(SELECT_ID_BY_NAME)) {
            Blob pictureBlob = connection.createBlob();
            byte[] pictureBytes = CustomPictureEncoder.decodeString(menuItem.getPictureBase64());
            pictureBlob.setBytes(1, pictureBytes);
            statement.setLong(1, menuItem.getSectionId());
            statement.setString(2, menuItem.getName());
            statement.setBlob(3, pictureBlob);
            statement.setBigDecimal(4, menuItem.getCost());
            if (statement.executeUpdate() != 0) {
                isInserted = true;
            }
            idStatement.setString(1, menuItem.getName());
            ResultSet resultSet = idStatement.executeQuery();
            if (resultSet.next()) {
                menuItem.setId(resultSet.getLong("mi_id"));
            }
            // FIXME: 22.04.2022
            insertMenuItemIngredientsMerge(connection, menuItem);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return isInserted;
    }

    @Override
    public boolean remove(MenuItem menuComposite) {
        return false;
    }

    @Override
    public List<MenuItem> findAll() throws DaoException {
        List<MenuItem> menuItems = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_MENU_ITEMS_JOIN_INGREDIENTS)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            MenuItemRowMapper mapper = new MenuItemRowMapper();
            while (!resultSet.isAfterLast()) {
                mapper.map(resultSet).ifPresent(menuItems::add);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return menuItems;
    }

    @Override
    public void update(long id, MenuItem menuItem) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement deleteIngredientStatement = connection.prepareStatement(DELETE_MENU_ITEM_INGREDIENTS_MERGE);
             PreparedStatement updateMenuItemStatement = connection.prepareStatement(UPDATE_MENU_ITEM_BY_ID)) {
            connection.setAutoCommit(false);
            updateMenuItemStatement.setString(1, menuItem.getName());

            Blob pictureBlob = connection.createBlob();
            byte[] pictureBytes = CustomPictureEncoder.decodeString(menuItem.getPictureBase64());
            pictureBlob.setBytes(1, pictureBytes);
            updateMenuItemStatement.setLong(1, menuItem.getId());
            updateMenuItemStatement.setString(2, menuItem.getName());
            updateMenuItemStatement.setBlob(3, pictureBlob);
            updateMenuItemStatement.setBigDecimal(4, menuItem.getCost());
            updateMenuItemStatement.setLong(5, menuItem.getSectionId());
            updateMenuItemStatement.setLong(6, id);
            updateMenuItemStatement.executeUpdate();

            deleteIngredientStatement.setLong(1, menuItem.getId());
            deleteIngredientStatement.executeUpdate();

            insertMenuItemIngredientsMerge(connection, menuItem);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                logger.log(Level.ERROR, exception);
            }

            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException exception) {
                logger.log(Level.ERROR, exception);
            }
        }
    }

    @Override
    public List<MenuItem> findBySectionId(long sectionId) throws DaoException {
        List<MenuItem> items = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_ALL_JOIN_INGREDIENTS_BY_SECTION_ID)) {
            statement.setLong(1, sectionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                CustomRowMapper<MenuItem> mapper = new MenuItemRowMapper();
                while (resultSet.next()) {
                    mapper.map(resultSet).ifPresent(items::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return items;
    }

    @Override
    public List<MenuItem> findByName(String name) throws DaoException {
        List<MenuItem> items = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_MENU_ITEMS_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                CustomRowMapper<MenuItem> mapper = new MenuItemRowMapper();
                while (resultSet.next()) {
                    mapper.map(resultSet).ifPresent(items::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return items;
    }

    private void insertMenuItemIngredientsMerge(Connection connection, MenuItem menuItem) throws SQLException {
        try (PreparedStatement newStatement = connection.prepareStatement(INSERT_MENU_ITEM_INGREDIENTS_MERGE)) {
            for (Ingredient ingredient : menuItem.getIngredients()) {
                newStatement.setLong(1, menuItem.getId());
                newStatement.setLong(2, ingredient.getId());
                newStatement.setDouble(3, ingredient.getWeight());
                newStatement.executeUpdate();
            }
        }
    }
}

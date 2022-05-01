package com.mahanko.finalproject.model.dao.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.dao.MenuItemDao;
import com.mahanko.finalproject.model.entity.menu.IngredientComponent;
import com.mahanko.finalproject.model.entity.menu.MenuItemComponent;
import com.mahanko.finalproject.model.entity.menu.MenuItemComposite;
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
    private static final String INSERT_NEW_MENU_ITEM =
            "INSERT INTO menu_items(mi_section, mi_name, mi_description, mi_picture) " +
                    "VALUE (?, ?, ?, ?)";
    private static final String SELECT_ID_BY_NAME = "SELECT mi_id FROM menu_items WHERE mi_name = ?";
    private static final String INSERT_MENU_ITEM_INGREDIENTS_MERGE =
            "INSERT INTO m2m_menuitems_ingredients(mi_id, ingr_id, ingr_weight) " +
                    "VALUE (?, ?, ?)";
    private static final String SELECT_ALL_MENU_ITEMS_JOIN_INGREDIENTS =
            "SELECT menu_items.mi_id, mi_section, mi_name, mi_description, mi_picture," +
                    "m2m_menuitems_ingredients.ingr_weight," +
                    "ingredients.ingr_id, ingr_name, ingr_proteins, ingr_fats, ingr_carbohydrates, ingr_calories, ingr_picture " +
                    "FROM menu_items " +
                    "INNER JOIN m2m_menuitems_ingredients " +
                    "ON menu_items.mi_id = m2m_menuitems_ingredients.mi_id " +
                    "INNER JOIN ingredients " +
                    "ON ingredients.ingr_id = m2m_menuitems_ingredients.ingr_id";
    private static final MenuItemDaoImpl instance = new MenuItemDaoImpl();

    private MenuItemDaoImpl() {
    }

    public static MenuItemDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<MenuItemComposite> findById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean insert(MenuItemComposite menuItem) throws DaoException {
        boolean isInserted = false;
        // FIXME: 22.04.2022 transactions or batch?
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW_MENU_ITEM);
             PreparedStatement idStatement = connection.prepareStatement(SELECT_ID_BY_NAME)) {
            Blob pictureBlob = connection.createBlob();
            byte[] pictureBytes = CustomPictureEncoder.decodeString(menuItem.getPictureBase64());
            pictureBlob.setBytes(1, pictureBytes);
            statement.setInt(1, menuItem.getSection().getId());
            statement.setString(2, menuItem.getName());
            statement.setString(3, menuItem.getDescription());
            statement.setBlob(4, pictureBlob);
            if (statement.executeUpdate() != 0) {
                isInserted = true;
            }
            idStatement.setString(1, menuItem.getName());
            ResultSet resultSet = idStatement.executeQuery();
            if (resultSet.next()) {
                menuItem.setId(resultSet.getLong("mi_id"));
            }
            // FIXME: 22.04.2022
            try (PreparedStatement newStatement = connection.prepareStatement(INSERT_MENU_ITEM_INGREDIENTS_MERGE)) {
                for (MenuItemComponent component : menuItem.getIngredients()) {
                    IngredientComponent ingredient = (IngredientComponent) component;
                    newStatement.setLong(1, menuItem.getId());
                    newStatement.setLong(2, ingredient.getId());
                    // FIXME: 22.04.2022 not constant!!!
                    newStatement.setDouble(3, ingredient.getWeight());
                    newStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return isInserted;
    }

    @Override
    public boolean remove(MenuItemComposite menuComposite) throws DaoException {
        return false;
    }

    @Override
    public List<MenuItemComposite> findAll() throws DaoException {
        List<MenuItemComposite> menuItems = new ArrayList<>();
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
    public MenuItemComposite update(long id, MenuItemComposite menuComposite) throws DaoException {
        return null;
    }
}

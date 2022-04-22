package com.mahanko.finalproject.model.dao.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.dao.MenuItemDao;
import com.mahanko.finalproject.model.entity.menu.IngredientComponent;
import com.mahanko.finalproject.model.entity.menu.MenuItemComponent;
import com.mahanko.finalproject.model.entity.menu.MenuItemComposite;
import com.mahanko.finalproject.model.pool.ConnectionPool;
import com.mahanko.finalproject.util.CustomStringEncoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class MenuItemDaoImpl implements MenuItemDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String INSERT_NEW_MENU_ITEM =
            "INSERT INTO menu_items(mi_section, mi_name, mi_description, mi_picture) " +
            "VALUE (?, ?, ?, ?)";
    private static final String INSERT_MENU_ITEM_INGREDIENTS_CONNECTION = 
            "INSERT INTO m2m_menuitems_ingredients(mi_id, ingr_id, ingr_weight) " +
            "VALUE (?, ?, ?)";
    private static final MenuItemDaoImpl instance = new MenuItemDaoImpl();

    private MenuItemDaoImpl() {
    }

    public static MenuItemDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(MenuItemComposite menuItem) throws DaoException {
        boolean isInserted = false;
        // FIXME: 22.04.2022 transactions?
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_NEW_MENU_ITEM)) {
            Blob pictureBlob = connection.createBlob();
            byte[] pictureBytes = CustomStringEncoder.decodeString(menuItem.getPictureBse64());
            pictureBlob.setBytes(1, pictureBytes);
            statement.setInt(1, menuItem.getSection().getId());
            statement.setString(2, menuItem.getName());
            statement.setString(3, menuItem.getDescription());
            statement.setBlob(4, pictureBlob);
            if (statement.executeUpdate() != 0) {
                isInserted = true;
            }
            // FIXME: 22.04.2022
            try (PreparedStatement newStatement = connection.prepareStatement(INSERT_MENU_ITEM_INGREDIENTS_CONNECTION)) {
                for (MenuItemComponent component : menuItem.getIngredients()) {
                    IngredientComponent ingredient = (IngredientComponent) component;
                    newStatement.setLong(1, menuItem.getId());
                    newStatement.setLong(2, ingredient.getId());
                    // FIXME: 22.04.2022 not constant!!!
                    newStatement.setDouble(3, 10);
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
        return null;
    }

    @Override
    public MenuItemComposite update(long id, MenuItemComposite menuComposite) throws DaoException {
        return null;
    }
}

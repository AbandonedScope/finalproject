package com.mahanko.finalproject.model.mapper.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.Ingredient;
import com.mahanko.finalproject.model.entity.menu.MenuItem;
import com.mahanko.finalproject.model.mapper.CustomRowMapper;
import com.mahanko.finalproject.util.CustomPictureEncoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.mahanko.finalproject.model.mapper.ColumnName.*;

/**
 * The type MenuItemRowMapper class. Maps result set to the MenuItem class object.
 */
public class MenuItemRowMapper implements CustomRowMapper<MenuItem> {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<MenuItem> map(ResultSet resultSet) throws DaoException {
        MenuItem menuItem = new MenuItem();
        Optional<MenuItem> menuItemOptional;
        try {
            Blob blob = resultSet.getBlob(MENU_ITEM_PICTURE);
            String menuItemPictureString = CustomPictureEncoder.arrayToBase64(blob.getBinaryStream().readAllBytes());
            menuItem.setId(resultSet.getLong(MENU_ITEM_ID));
            menuItem.setName(resultSet.getString(MENU_ITEM_NAME));
            menuItem.setPictureBase64(menuItemPictureString);
            menuItem.setCost(resultSet.getBigDecimal(MENU_ITEM_COST));
            menuItem.setSectionId(resultSet.getLong(MENU_ITEM_SECTION));
            IngredientRowMapper ingredientMapper = new IngredientRowMapper();
            do {
                Optional<Ingredient> ingredientOptional = ingredientMapper.map(resultSet);
                if (ingredientOptional.isPresent()) {
                    ingredientOptional.get().setWeight(resultSet.getDouble(INGREDIENT_WEIGHT));
                    menuItem.addIngredient(ingredientOptional.get());
                }
            } while (resultSet.next() && resultSet.getLong(MENU_ITEM_ID) == menuItem.getId());

            menuItemOptional = Optional.of(menuItem);
        } catch (SQLException | IOException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return menuItemOptional;
    }
}

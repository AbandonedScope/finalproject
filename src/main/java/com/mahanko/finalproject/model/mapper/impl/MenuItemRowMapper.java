package com.mahanko.finalproject.model.mapper.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.IngredientComponent;
import com.mahanko.finalproject.model.entity.menu.MenuItemComposite;
import com.mahanko.finalproject.model.entity.menu.MenuItemCompositeLevel;
import com.mahanko.finalproject.model.mapper.ColumnName;
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

public class MenuItemRowMapper implements CustomRowMapper<MenuItemComposite> {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<MenuItemComposite> map(ResultSet resultSet) throws DaoException {
        MenuItemComposite menuItem = new MenuItemComposite(MenuItemCompositeLevel.MENU_ITEM);
        Optional<MenuItemComposite> menuItemOptional;
        try {
            Blob blob = resultSet.getBlob(ColumnName.MENU_ITEM_PICTURE);
            String menuItemPictureString = CustomPictureEncoder.arrayToBase64(blob.getBinaryStream().readAllBytes());
            menuItem.setId(resultSet.getLong(ColumnName.MENU_ITEM_ID));
            menuItem.setName(resultSet.getString(ColumnName.MENU_ITEM_NAME));
            menuItem.setDescription(resultSet.getString(ColumnName.MENU_ITEM_DESCRIPTION));
            menuItem.setPictureBase64(menuItemPictureString);
            menuItem.setCost(resultSet.getBigDecimal(ColumnName.MENU_ITEM_COST));
            // FIXME: 27.04.2022 add section
            IngredientRowMapper ingredientMapper = new IngredientRowMapper();
            do {
                Optional<IngredientComponent> ingredientOptional = ingredientMapper.map(resultSet);
                if (ingredientOptional.isPresent()) {
                    ingredientOptional.get().setWeight(resultSet.getDouble(ColumnName.INGREDIENT_WEIGHT));
                    menuItem.addIngredient(ingredientOptional.get());
                }
            } while (resultSet.next() && resultSet.getLong(ColumnName.MENU_ITEM_ID) == menuItem.getId());
            menuItemOptional = Optional.of(menuItem);
        } catch (SQLException | IOException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return menuItemOptional;
    }
}

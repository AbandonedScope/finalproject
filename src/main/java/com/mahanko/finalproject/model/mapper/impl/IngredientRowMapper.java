package com.mahanko.finalproject.model.mapper.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.Ingredient;
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

public class IngredientRowMapper implements CustomRowMapper<Ingredient> {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<Ingredient> map(ResultSet resultSet) throws DaoException {
        Optional<Ingredient> ingredientOptional = Optional.empty();
        try {
            Blob blob = resultSet.getBlob(ColumnName.INGREDIENT_PICTURE);
            String picture = CustomPictureEncoder.arrayToBase64(blob.getBinaryStream().readAllBytes());
            Ingredient ingredient = Ingredient.newBuilder()
                    .setId(resultSet.getLong(ColumnName.INGREDIENT_ID))
                    .setName(resultSet.getString(ColumnName.INGREDIENT_NAME))
                    .setProteins(resultSet.getDouble(ColumnName.INGREDIENT_PROTEINS))
                    .setFats(resultSet.getDouble(ColumnName.INGREDIENT_FATS))
                    .setCarbohydrates(resultSet.getDouble(ColumnName.INGREDIENT_CARBOHYDRATES))
                    .setCalories(resultSet.getDouble(ColumnName.INGREDIENT_CALORIES))
                    .setPicture(picture).build();
            ingredientOptional = Optional.of(ingredient);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ingredientOptional;
    }
}

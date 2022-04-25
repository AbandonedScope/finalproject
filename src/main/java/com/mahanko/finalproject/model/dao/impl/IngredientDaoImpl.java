package com.mahanko.finalproject.model.dao.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.dao.IngredientDao;
import com.mahanko.finalproject.model.entity.menu.IngredientComponent;
import com.mahanko.finalproject.model.mapper.impl.IngredientRowMapper;
import com.mahanko.finalproject.model.pool.ConnectionPool;
import com.mahanko.finalproject.util.CustomStringEncoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDaoImpl implements IngredientDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SELECT_ALL_INGREDIENTS = "SELECT * FROM ingredients";
    private static final String SELECT_EXISTS_INGREDIENT_BY_NAME = "SELECT EXISTS (SELECT ingr_id FROM ingredients WHERE ingr_name = ?)";
    private static final String INSERT_NEW_INGREDIENTS =
            "INSERT INTO ingredients(ingr_name, ingr_proteins, ingr_fats, ingr_carbohydrates, ingr_calories, ingr_picture)" +
                    " VALUE (?, ?, ?, ?, ?, ?)";
    private static final IngredientDaoImpl instance = new IngredientDaoImpl();

    private IngredientDaoImpl() {
    }

    public static IngredientDaoImpl getInstance() {
        return instance;
    }


    @Override
    public boolean existWithName(String name) throws DaoException {
        boolean exists = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_EXISTS_INGREDIENT_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                exists = resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return exists;
    }

    // FIXME: 22.04.2022 equal names
    @Override
    public boolean insert(IngredientComponent ingredientComponent) throws DaoException {
        boolean isInserted = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW_INGREDIENTS)) {
            String base64String = ingredientComponent.getPicture();
            byte[] data = CustomStringEncoder.decodeString(base64String);
            Blob blob = connection.createBlob();
            blob.setBytes(1, data);
            statement.setString(1, ingredientComponent.getName());
            statement.setDouble(2, ingredientComponent.getProteins());
            statement.setDouble(3, ingredientComponent.getFats());
            statement.setDouble(4, ingredientComponent.getCarbohydrates());
            statement.setDouble(5, ingredientComponent.getCalories());
            statement.setBlob(6, blob);
            if (statement.executeUpdate() != 0) {
                isInserted = true;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return isInserted;
    }

    @Override
    public boolean remove(IngredientComponent ingredientComponent) throws DaoException {
        return false;
    }

    @Override
    public List<IngredientComponent> findAll() throws DaoException {
        List<IngredientComponent> ingredients = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_INGREDIENTS)) {
            ResultSet result = statement.executeQuery();
            IngredientRowMapper mapper = new IngredientRowMapper();
            while (result.next()) {
                mapper.map(result).ifPresent(ingredients::add);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return ingredients;
    }

    @Override
    public IngredientComponent update(long id, IngredientComponent ingredientComponent) throws DaoException {
        return null;
    }
}
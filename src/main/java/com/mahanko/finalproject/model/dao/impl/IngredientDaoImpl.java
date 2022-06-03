package com.mahanko.finalproject.model.dao.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.mapper.CustomRowMapper;
import com.mahanko.finalproject.model.mapper.impl.IngredientRowMapper;
import com.mahanko.finalproject.model.dao.IngredientDao;
import com.mahanko.finalproject.model.entity.menu.Ingredient;
import com.mahanko.finalproject.model.pool.ConnectionPool;
import com.mahanko.finalproject.util.CustomPictureEncoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IngredientDaoImpl implements IngredientDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SELECT_BY_ID =
            "SELECT ingr_id, ingr_name, ingr_proteins, ingr_fats, " +
                    "ingr_carbohydrates, ingr_calories, ingr_picture " +
                    "FROM ingredients WHERE ingr_id = ?";
    private static final String SELECT_ALL_INGREDIENTS =
            "SELECT ingr_id, ingr_name, ingr_proteins, ingr_fats, " +
                    "ingr_carbohydrates, ingr_calories, ingr_picture " +
                    "FROM ingredients";
    private static final String SELECT_EXISTS_INGREDIENT_BY_NAME =
            "SELECT EXISTS (SELECT ingr_id FROM ingredients WHERE ingr_name = ?)";
    private static final String INSERT_NEW_INGREDIENTS =
            "INSERT INTO ingredients(ingr_name, ingr_proteins, ingr_fats, " +
                    "ingr_carbohydrates, ingr_calories, ingr_picture)" +
                    " VALUE (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_NAME =
            "SELECT ingr_id, ingr_name, ingr_proteins, ingr_fats, " +
                    " ingr_carbohydrates, ingr_calories, ingr_picture from ingredients " +
                    "WHERE locate(?, ingr_name) > 0";
    private static final String UPDATE_BY_ID =
            "update ingredients set ingr_id = ?, ingr_name = ?, ingr_proteins = ?, " +
                    "ingr_fats = ?, ingr_carbohydrates = ?, ingr_calories = ?, " +
                    "ingr_picture = ? " +
                    "where ingr_id = ?";
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
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    exists = resultSet.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return exists;
    }

    @Override
    public List<Ingredient> findByName(String name) throws DaoException {
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                CustomRowMapper<Ingredient> mapper = new IngredientRowMapper();
                while (resultSet.next()) {
                    mapper.map(resultSet).ifPresent(ingredients::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return ingredients;
    }

    @Override
    public Optional<Ingredient> findById(Long id) throws DaoException {
        Optional<Ingredient> ingredientOptional = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    IngredientRowMapper mapper = new IngredientRowMapper();
                    ingredientOptional = mapper.map(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return ingredientOptional;
    }

    // FIXME: 22.04.2022 equal names
    @Override
    public boolean insert(Ingredient ingredient) throws DaoException {
        boolean isInserted = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW_INGREDIENTS, Statement.RETURN_GENERATED_KEYS)) {
            String base64String = ingredient.getPictureBase64();
            byte[] data = CustomPictureEncoder.decodeString(base64String);
            Blob blob = connection.createBlob();
            blob.setBytes(1, data);
            statement.setString(1, ingredient.getName());
            statement.setDouble(2, ingredient.getProteins());
            statement.setDouble(3, ingredient.getFats());
            statement.setDouble(4, ingredient.getCarbohydrates());
            statement.setDouble(5, ingredient.getCalories());
            statement.setBlob(6, blob);
            if (statement.executeUpdate() != 0) {
                isInserted = true;
                try (ResultSet key = statement.getGeneratedKeys()) {
                    key.next();
                    long ingredientId = key.getLong(1);
                    ingredient.setId(ingredientId);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return isInserted;
    }

    @Override
    public boolean remove(Ingredient ingredient) throws DaoException {
        return false;
    }

    @Override
    public List<Ingredient> findAll() throws DaoException {
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_INGREDIENTS)) {
            try (ResultSet result = statement.executeQuery()) {
                IngredientRowMapper mapper = new IngredientRowMapper();
                while (result.next()) {
                    mapper.map(result).ifPresent(ingredients::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return ingredients;
    }

    @Override
    public boolean update(long id, Ingredient ingredient) throws DaoException {
        boolean isInserted = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID)) {
            String base64String = ingredient.getPictureBase64();
            byte[] data = CustomPictureEncoder.decodeString(base64String);
            Blob blob = connection.createBlob();
            blob.setBytes(1, data);
            statement.setLong(1, ingredient.getId());
            statement.setString(2, ingredient.getName());
            statement.setDouble(3, ingredient.getProteins());
            statement.setDouble(4, ingredient.getFats());
            statement.setDouble(5, ingredient.getCarbohydrates());
            statement.setDouble(6, ingredient.getCalories());
            statement.setBlob(7, blob);
            statement.setLong(8, id);
            if (statement.executeUpdate() != 0) {
                isInserted = true;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return isInserted;
    }
}

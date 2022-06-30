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
                    "FROM ingredients where ingr_hidden = 0";
    private static final String SELECT_EXISTS_INGREDIENT_BY_NAME =
            "SELECT ingr_id FROM ingredients WHERE ingr_name = ? and ingr_hidden = 0 limit 1";
    private static final String INSERT_NEW_INGREDIENTS =
            "INSERT INTO ingredients(ingr_name, ingr_proteins, ingr_fats, " +
                    "ingr_carbohydrates, ingr_calories, ingr_picture)" +
                    " VALUE (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_NAME =
            "SELECT ingr_id, ingr_name, ingr_proteins, ingr_fats, " +
                    " ingr_carbohydrates, ingr_calories, ingr_picture from ingredients " +
                    "WHERE locate(?, ingr_name) > 0 and ingr_hidden = 0";
    private static final String UPDATE_BY_ID =
            "update ingredients set ingr_id = ?, ingr_name = ?, ingr_proteins = ?, " +
                    "ingr_fats = ?, ingr_carbohydrates = ?, ingr_calories = ?, " +
                    "ingr_picture = ? " +
                    "where ingr_id = ?";
    private static final String SELECT_EXISTS_IN_MENU_ITEMS_MERGE_BY_ID =
            "select ingr_id from m2m_menuitems_ingredients where ingr_id = ? limit 1";
    private static final String UPDATE_HIDDEN_BY_ID =
            "update ingredients set ingr_hidden = ? where ingr_id = ?";
    private static final String REMOVE_BY_ID =
            "delete from ingredients where ingr_id = ?";
    private static final IngredientDaoImpl instance = new IngredientDaoImpl();

    private IngredientDaoImpl() {
    }

    public static IngredientDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean existsWithName(String name) throws DaoException {
        boolean exists = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_EXISTS_INGREDIENT_BY_NAME)) {
            statement.setString(1, name);
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
    public boolean existsMerge(long ingredientId) throws DaoException {
        boolean exists = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_EXISTS_IN_MENU_ITEMS_MERGE_BY_ID)) {
            statement.setLong(1, ingredientId);
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
    public Optional<Ingredient> findById(long id) throws DaoException {
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
    public boolean insert(Ingredient id) throws DaoException {
        boolean isInserted = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW_INGREDIENTS, Statement.RETURN_GENERATED_KEYS)) {
            String base64String = id.getPictureBase64();
            byte[] data = CustomPictureEncoder.decodeString(base64String);
            Blob blob = connection.createBlob();
            blob.setBytes(1, data);
            statement.setString(1, id.getName());
            statement.setDouble(2, id.getProteins());
            statement.setDouble(3, id.getFats());
            statement.setDouble(4, id.getCarbohydrates());
            statement.setDouble(5, id.getCalories());
            statement.setBlob(6, blob);
            if (statement.executeUpdate() != 0) {
                isInserted = true;
                try (ResultSet key = statement.getGeneratedKeys()) {
                    key.next();
                    long ingredientId = key.getLong(1);
                    id.setId(ingredientId);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return isInserted;
    }

    @Override
    public boolean remove(Long id) throws DaoException {
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
    public boolean setHidden(Long id, boolean state) throws DaoException {
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
    public boolean update(long id, Ingredient entity) throws DaoException {
        boolean isInserted = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID)) {
            String base64String = entity.getPictureBase64();
            byte[] data = CustomPictureEncoder.decodeString(base64String);
            Blob blob = connection.createBlob();
            blob.setBytes(1, data);
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.setDouble(3, entity.getProteins());
            statement.setDouble(4, entity.getFats());
            statement.setDouble(5, entity.getCarbohydrates());
            statement.setDouble(6, entity.getCalories());
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

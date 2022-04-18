package com.mahanko.finalproject.model.dao.impl;

import com.mahanko.finalproject.model.dao.RoleDao;
import com.mahanko.finalproject.model.entity.RoleEntity;
import com.mahanko.finalproject.model.entity.RoleType;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RoleDaoImpl implements RoleDao {
    private static final String INSERT_NEW_ROLE = "INSERT roles(r_type) VALUE (?)";
    private static final String SELECT_ID_BY_TYPE = "SELECT r_id FROM roles WHERE r_type = ? LIMIT 1";
    private static final Logger logger = LogManager.getLogger();
    private static RoleDaoImpl instance = new RoleDaoImpl();

    private RoleDaoImpl() {
    }

    public static RoleDaoImpl getInstance() {
        return instance;
    }


    @Override
    public boolean insert(RoleEntity roleEntity) throws DaoException {
        boolean isInserted;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW_ROLE)) {
            statement.setString(1, roleEntity.getType().toString());
            isInserted = statement.executeUpdate() != 0;
            ConnectionPool.getInstance().releaseConnection(connection);
        } catch (SQLException e) {
            logger.log(Level.INFO, e);
            throw new DaoException(e);
        }

        return isInserted;
    }

    @Override
    public boolean remove(RoleEntity roleEntity) throws DaoException {
        return false;
    }

    @Override
    public List<RoleEntity> findAll() throws DaoException {
        return null;
    }

    @Override
    public RoleEntity update(long id, RoleEntity roleEntity) throws DaoException {
        return null;
    }

    @Override
    public int getIdByType(RoleType type) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ID_BY_TYPE)) {
            statement.setString(1, type.toString());
            ResultSet result = statement.executeQuery();
            if (result.next()) { // FIXME: 15.04.2022  exception?
                return result.getInt(1);
            } else {
                logger.log(Level.ERROR, "Type {} was not found in data base.", type);
                throw new DaoException(String.format("Type %s was not found in data base.", type));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }
}

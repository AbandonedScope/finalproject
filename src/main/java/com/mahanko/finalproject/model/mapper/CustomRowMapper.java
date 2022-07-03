package com.mahanko.finalproject.model.mapper;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.AbstractEntity;

import java.sql.ResultSet;
import java.util.Optional;

/**
 * The interface CustomRowMapper.
 *
 * @param <E> the type of the entity to map to.
 */
public interface CustomRowMapper<E extends AbstractEntity> {
    /**
     * Maps the given result set to the entity.
     *
     * @param resultSet the result set
     * @return the entity optional
     * @throws DaoException - if the was any SQLException during database operations.
     */
    Optional<E> map(ResultSet resultSet) throws DaoException;
}

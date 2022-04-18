package com.mahanko.finalproject.model.mapper;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.AbstractEntity;

import java.sql.ResultSet;
import java.util.Optional;

public interface CustomRowMapper<E extends AbstractEntity> {
    Optional<E> map(ResultSet resultSet) throws DaoException;
}

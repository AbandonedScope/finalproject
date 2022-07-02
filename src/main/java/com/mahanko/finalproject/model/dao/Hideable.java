package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.exception.DaoException;

public interface Hideable<T> {
    boolean updateHidden(T id, boolean state) throws DaoException;
}

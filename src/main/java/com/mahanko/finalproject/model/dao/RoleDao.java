package com.mahanko.finalproject.model.dao;

import com.mahanko.finalproject.model.entity.RoleEntity;
import com.mahanko.finalproject.model.entity.RoleType;
import com.mahanko.finalproject.exception.DaoException;

public interface RoleDao extends BaseDao<RoleEntity> {
    int getIdByType(RoleType type) throws DaoException;
}

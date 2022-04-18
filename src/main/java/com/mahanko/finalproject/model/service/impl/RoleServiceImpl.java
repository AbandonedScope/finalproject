package com.mahanko.finalproject.model.service.impl;

import com.mahanko.finalproject.model.dao.impl.RoleDaoImpl;
import com.mahanko.finalproject.model.entity.RoleEntity;
import com.mahanko.finalproject.model.entity.RoleType;
import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.RoleService;

public class RoleServiceImpl implements RoleService {
    private static RoleServiceImpl instance;

    private RoleServiceImpl() {
    }

    public static RoleServiceImpl getInstance() {
        if (instance == null) {
            instance = new RoleServiceImpl();
        }

        return instance;
    }

    @Override
    public RoleEntity getRoleByType(RoleType type) throws ServiceException {
        try {
            int id = RoleDaoImpl.getInstance().getIdByType(type);
            return new RoleEntity(id, type);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}

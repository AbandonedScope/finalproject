package com.mahanko.finalproject.model.service;

import com.mahanko.finalproject.model.entity.RoleEntity;
import com.mahanko.finalproject.model.entity.RoleType;
import com.mahanko.finalproject.exception.ServiceException;

public interface RoleService {
    RoleEntity getRoleByType(RoleType type) throws ServiceException;
}

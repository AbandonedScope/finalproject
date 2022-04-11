package com.mahanko.finalproject.dao.impl;

import com.mahanko.finalproject.dao.BaseDao;
import com.mahanko.finalproject.entity.AbstractEntity;

import java.util.List;

public class BaseDaoImpl<E extends AbstractEntity> implements BaseDao<E> {

    @Override
    public boolean insert(E e) {
        return false;
    }

    @Override
    public boolean remove(E e) {
        return false;
    }

    @Override
    public List<E> findAll() {
        return null;
    }

    @Override
    public E update(long id, E e) {
        return null;
    }
}

package com.manager.chat.dao;

import com.manager.chat.entity.BaseEntity;
import java.util.List;

public interface BaseDao<T extends BaseEntity> {

    T add(T entity);
    void update(T entity);
    T get(int id);
    void delete(int id);
    void delete(T entity);
    List<T> getAll();
}

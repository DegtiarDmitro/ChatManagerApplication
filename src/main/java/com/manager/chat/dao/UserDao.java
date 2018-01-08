package com.manager.chat.dao;

import com.manager.chat.entity.User;

public interface UserDao extends BaseDao<User> {

    User getOneByName(String name);
}

package com.manager.chat.dao;

import com.manager.chat.entity.Manager;

public interface ManagerDao extends BaseDao<Manager>{

	Manager getOneByName(String name);
}

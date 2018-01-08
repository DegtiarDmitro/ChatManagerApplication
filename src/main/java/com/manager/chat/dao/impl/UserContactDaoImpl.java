package com.manager.chat.dao.impl;

import com.manager.chat.dao.UserContactDao;
import com.manager.chat.entity.UserContact;

public class UserContactDaoImpl extends BaseDaoImpl<UserContact> implements UserContactDao {
	
	
	public UserContactDaoImpl() {
        super();
        GET_ALL_HQL = "From UserContact";
        DELETE_BY_ID_HQL = "DELETE FROM UserContact WHERE ID = :id";
        GET_ONE_BY_ID_HQL =  "FROM UserContact WHERE ID = :id";
    }
	
}

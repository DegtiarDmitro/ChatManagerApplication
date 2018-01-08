package com.manager.chat.dao.impl;

import com.manager.chat.dao.MessageDao;
import com.manager.chat.entity.Message;

public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao {
	
	public MessageDaoImpl() {
        super();
        GET_ALL_HQL = "From Message";
        DELETE_BY_ID_HQL = "DELETE FROM Message WHERE ID = :id";
        GET_ONE_BY_ID_HQL =  "FROM Message WHERE ID = :id";
    }
}

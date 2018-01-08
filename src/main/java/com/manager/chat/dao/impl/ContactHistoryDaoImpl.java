package com.manager.chat.dao.impl;

import com.manager.chat.dao.ContactHistoryDao;
import com.manager.chat.entity.ContactHistory;

public class ContactHistoryDaoImpl extends BaseDaoImpl<ContactHistory> implements ContactHistoryDao{

	public ContactHistoryDaoImpl() {
        super();
        GET_ALL_HQL = "From ContactHistory";
        DELETE_BY_ID_HQL = "DELETE FROM ContactHistory WHERE ID = :id";
        GET_ONE_BY_ID_HQL =  "FROM ContactHistory WHERE ID = :id";
    }
}

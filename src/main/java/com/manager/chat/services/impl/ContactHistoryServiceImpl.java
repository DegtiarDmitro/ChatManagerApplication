package com.manager.chat.services.impl;

import com.manager.chat.dao.ContactHistoryDao;
import com.manager.chat.dao.impl.ContactHistoryDaoImpl;
import com.manager.chat.entity.ContactHistory;
import com.manager.chat.services.ContactHistoryService;

public class ContactHistoryServiceImpl implements ContactHistoryService {

    private static ContactHistoryServiceImpl instance = null;
    private ContactHistoryDao contactHistoryDao = null;

    private ContactHistoryServiceImpl(){
        contactHistoryDao = new ContactHistoryDaoImpl();
    }

    public static ContactHistoryServiceImpl getInstance(){

        if(instance == null){
            instance = new ContactHistoryServiceImpl();
        }
        return instance;
    }

    @Override
    public ContactHistory getContactHistory(int id) {
        return contactHistoryDao.get(id);
    }

	@Override
	public void addCotactHistory(ContactHistory contactHistory) {
		contactHistoryDao.add(contactHistory);
		
	}
}

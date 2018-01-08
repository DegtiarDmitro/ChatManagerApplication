package com.manager.chat.services.impl;

import com.manager.chat.dao.UserContactDao;
import com.manager.chat.dao.impl.UserContactDaoImpl;
import com.manager.chat.entity.UserContact;
import com.manager.chat.services.UserContactService;


public class UserContactServiceImpl implements UserContactService {


    private static UserContactServiceImpl instance = null;
    private UserContactDao userContactDao = null;

    private UserContactServiceImpl(){
        userContactDao = new UserContactDaoImpl();
    }

    public static UserContactServiceImpl getInstance(){
        if(instance == null){
            instance = new UserContactServiceImpl();
        }
        return instance;
    }




    @Override
    public UserContact addUserContact(UserContact userContact) {
        return userContactDao.add(userContact);
    }

    @Override
    public void updateUserContact(UserContact userContact) {
        userContactDao.update(userContact);
    }

    @Override
    public void removeUserContact(UserContact userContact) {
        userContactDao.delete(userContact);
    }





	
}

package com.manager.chat.services;


import com.manager.chat.entity.UserContact;

public interface UserContactService {

    UserContact addUserContact(UserContact userContact);

    void updateUserContact(UserContact userContact);

    void removeUserContact(UserContact userContact);

}

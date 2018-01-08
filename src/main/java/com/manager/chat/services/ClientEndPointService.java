package com.manager.chat.services;


import com.manager.chat.entity.Manager;
import com.manager.chat.entity.Message;
import com.manager.chat.entity.UserContact;

public interface ClientEndPointService {


    void validateUser(Manager user);

    void sendTextMessage(Message message);

    void sendBinaryMessage(Message message);
    
    void confirmNewContact(UserContact user);
}

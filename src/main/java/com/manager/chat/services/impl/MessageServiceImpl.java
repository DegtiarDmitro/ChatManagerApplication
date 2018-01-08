package com.manager.chat.services.impl;

import com.manager.chat.dao.MessageDao;
import com.manager.chat.dao.impl.MessageDaoImpl;
import com.manager.chat.entity.Message;
import com.manager.chat.services.MessageService;

public class MessageServiceImpl implements MessageService {

    private static MessageServiceImpl instance;
    private MessageDao messageDao = null;

    private MessageServiceImpl(){
        messageDao = new MessageDaoImpl();
    }

    public static MessageServiceImpl getInstance(){
        if(instance == null){
            instance = new MessageServiceImpl();
        }
        return instance;
    }


    @Override
    public Message addMessage(Message message) {
        return messageDao.add(message);
    }


}

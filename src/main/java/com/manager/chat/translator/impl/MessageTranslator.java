package com.manager.chat.translator.impl;

import com.manager.chat.entity.Message;
import com.manager.chat.services.ContactHistoryService;
import com.manager.chat.services.UserContactService;
import com.manager.chat.services.UserService;
import com.manager.chat.services.impl.ContactHistoryServiceImpl;
import com.manager.chat.services.impl.UserContactServiceImpl;
import com.manager.chat.services.impl.UserServiceImpl;
import com.manager.chat.translator.JSONTranslator;
import org.json.JSONObject;

/**
 *
 */
public class MessageTranslator implements JSONTranslator<Message> {


    private UserService userService = UserServiceImpl.getInstance();
    private ContactHistoryService contactHistoryService = ContactHistoryServiceImpl.getInstance();
    private UserContactService userContactService = UserContactServiceImpl.getInstance();
    
    @Override
    public Message fromJSON(JSONObject obj){
        Message message = new Message();

        message.setId(obj.getInt("message_id"));

        message.setAddressee(userService.getUser(obj.getInt("addressee_id")));
        message.setDestination(userService.getUser(obj.getInt("destination_id")));
        if(obj.has("contact_history_id")){
        	message.setContactHistory(contactHistoryService.getContactHistory(obj.getInt("contact_history_id")));
        }
        if(obj.has("message")){
            message.setMessage(obj.getString("message"));
        }
        return message;
    }

    @Override
    public JSONObject toJSON(Message message) {
        JSONObject messageObj = new JSONObject();
        messageObj.put("message_id", message.getId());
        messageObj.put("addressee_id", message.getAddressee().getId());
        messageObj.put("destination_id", message.getDestination().getId());
        messageObj.put("contact_history_id", message.getContactHistory().getId());
        if(message.getMessage() != null){
            messageObj.put("message", message.getMessage());
        }
        return messageObj;
    }
}

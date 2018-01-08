package com.manager.chat.translator.impl;

import com.manager.chat.entity.User;
import com.manager.chat.translator.JSONTranslator;
import org.json.JSONObject;

public class UserTranslator implements JSONTranslator<User> {


    @Override
    public User fromJSON(JSONObject obj) {

        User user = new User();

        user.setId(obj.getInt("user_id"));
        user.setUsername(obj.getString("user_name"));
        user.setRole(obj.getString("user_role"));
        user.setUserStatus(obj.getString("user_status"));
        if(obj.has("user_email")){
            user.setEmail(obj.getString("user_email"));
        }
        if(obj.has("user_phone")){
            user.setEmail(obj.getString("user_phone"));
        }
        return user;
    }

    @Override
    public JSONObject toJSON(User user) {
        JSONObject userObj = new JSONObject();
        userObj.put("user_id", user.getId());
        userObj.put("user_name", user.getUsername());
        userObj.put("user_role", user.getRole());
        userObj.put("user_status", user.getUserStatus());
        return userObj;
    }
}

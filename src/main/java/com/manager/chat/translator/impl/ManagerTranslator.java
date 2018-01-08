package com.manager.chat.translator.impl;

import com.manager.chat.entity.Manager;
import com.manager.chat.translator.JSONTranslator;
import org.json.JSONObject;

public class ManagerTranslator implements JSONTranslator<Manager> {
    @Override
    public Manager fromJSON(JSONObject obj) {

        Manager manager = new Manager();

        manager.setId(obj.getInt("user_id"));
        manager.setUsername(obj.getString("user_name"));
        manager.setRole(obj.getString("user_role"));
        manager.setUserStatus(obj.getString("user_status"));
        manager.setPassword(obj.getString("user_password"));
        return manager;
    }

    @Override
    public JSONObject toJSON(Manager manager) {
        JSONObject userObj = new JSONObject();
        userObj.put("user_name", manager.getUsername());
        userObj.put("user_role", manager.getRole());
        userObj.put("user_password", manager.getPassword());
        return userObj;
    }
}

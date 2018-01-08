package com.manager.chat.translator.impl;

import org.json.JSONObject;

import com.manager.chat.entity.UserContact;
import com.manager.chat.services.AuthenticationService;
import com.manager.chat.services.impl.AuthenticationServiceImpl;
import com.manager.chat.translator.JSONTranslator;

public class UserContactTranslator implements JSONTranslator<UserContact>{

	
	private UserTranslator userTranslator = new UserTranslator();
	private AuthenticationService authenticationService = AuthenticationServiceImpl.getInstance();
	
	
	@Override
	public UserContact fromJSON(JSONObject contactObj) {
		UserContact userContact = new UserContact();
		userContact.setId(contactObj.getInt("contact_id"));
		userContact.setContactUser(userTranslator.fromJSON(contactObj.getJSONObject("contact_user")));
		userContact.setUser(authenticationService.getAuthenticatedUser());
		return userContact;
	}

	
	@Override
	public JSONObject toJSON(UserContact entity) {
		JSONObject userObj = new JSONObject();
        userObj.put("contact_id", entity.getId());
        userObj.put("contact_user", entity.getContactUser().getId());
        userObj.put("user", entity.getUser().getId());
        userObj.put("contact_history", entity.getContactHistory().getId());
        return userObj;
	}

}

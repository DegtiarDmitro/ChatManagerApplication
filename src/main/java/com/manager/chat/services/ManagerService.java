package com.manager.chat.services;

import com.manager.chat.entity.Manager;

public interface ManagerService {
	
	void addManager(Manager manager);

	Manager getManagerByName(String name);

}

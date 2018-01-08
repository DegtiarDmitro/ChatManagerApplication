package com.manager.chat.services.impl;

import com.manager.chat.dao.ManagerDao;
import com.manager.chat.dao.impl.ManagerDaoImpl;
import com.manager.chat.entity.Manager;
import com.manager.chat.services.ManagerService;

public class ManagerServiceImpl implements ManagerService{
	
	private static ManagerServiceImpl instance = null;
	private ManagerDao managerDao = null;
	
	
	
	private ManagerServiceImpl() {
		managerDao = new ManagerDaoImpl();
	}
	
	public static ManagerServiceImpl getInstance() {
		
		if(instance == null) {
			instance = new ManagerServiceImpl();
		}
		
		return instance;
	}

	@Override
	public void addManager(Manager manager) {
		managerDao.add(manager);
	}


	@Override
	public Manager getManagerByName(String name) {
		return managerDao.getOneByName(name);
	}
	


}

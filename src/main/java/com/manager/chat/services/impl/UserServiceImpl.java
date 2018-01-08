package com.manager.chat.services.impl;


import com.manager.chat.dao.UserDao;
import com.manager.chat.dao.impl.UserDaoImpl;
import com.manager.chat.entity.User;
import com.manager.chat.services.UserService;


/**
 *
 */
public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance = null;
    private UserDao userDao = null;


    /**
     *
     */
    private UserServiceImpl(){
    	userDao = new UserDaoImpl();
    }

    /**
     *
     * @return -
     */
    public static UserServiceImpl getInstance(){
        if(instance == null){
            instance = new UserServiceImpl();
        }
        return instance;
    }


    @Override
    public User getUser(int id) {
        return userDao.get(id);
    }

    @Override
    public User getUserByName(String username) {
        return userDao.getOneByName(username);
    }

    @Override
    public User addUser(User user) {
    	return userDao.add(user);
    }


    
	@Override
	public void updateUser(User user) {
		userDao.update(user);
	}


	@Override
	public void removeUser(User user) {
		userDao.delete(user);
	}
    
}







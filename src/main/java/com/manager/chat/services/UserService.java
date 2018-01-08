package com.manager.chat.services;

import com.manager.chat.entity.User;

public interface UserService {

    User getUser(int id);
    User getUserByName(String username);
    User addUser(User user);
    void updateUser(User user);
    void removeUser(User user);
}

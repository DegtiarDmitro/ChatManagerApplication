package com.manager.chat.services;


import com.manager.chat.entity.Manager;

/**
 *
 */
public interface AuthenticationService {

    void authenticateUser(Manager manager);

    boolean isUserAuthenticated();

    void validateUser(Manager manager);

    boolean isUserValidated();

    Manager getAuthenticatedUser();
}

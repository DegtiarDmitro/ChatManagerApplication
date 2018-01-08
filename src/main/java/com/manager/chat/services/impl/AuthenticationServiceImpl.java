package com.manager.chat.services.impl;


import com.manager.chat.entity.Manager;
import com.manager.chat.services.AuthenticationService;
import com.manager.chat.services.ManagerService;


/**
 *
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private static AuthenticationServiceImpl instance = null;
    private Manager authenticatedUser = null;
    private ManagerService managerService = ManagerServiceImpl.getInstance();
    private boolean userValid = false;


    /**
     * private constructor
     */
    private AuthenticationServiceImpl(){}



    public static AuthenticationServiceImpl getInstance(){
        if(instance == null){
            instance = new AuthenticationServiceImpl();
        }
        return instance;
    }



    @Override
    public boolean isUserAuthenticated() {
        return authenticatedUser != null;
    }

    @Override
    public boolean isUserValidated() {
        return userValid;
    }


    @Override
    public void validateUser(Manager tempUser) {
        authenticatedUser = tempUser;
        ClientEndpointServiceImpl.getInstance().validateUser(tempUser);
    }



    @Override
    public void authenticateUser(Manager manager) {

        if(!authenticatedUser.getUsername().equals(manager.getUsername()) || !authenticatedUser.getPassword().equals(manager.getPassword())){
            return;
        }
        if(!authenticatedUser.equals(manager)){
            managerService.addManager(manager);
        }
        authenticatedUser = managerService.getManagerByName(manager.getUsername());
        userValid = true;
    }
    

    @Override
    public Manager getAuthenticatedUser() {
        return authenticatedUser;
    }


}

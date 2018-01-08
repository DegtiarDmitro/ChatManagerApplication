package com.manager.chat.controllers;

import com.manager.chat.Main;
import com.manager.chat.entity.Manager;
import com.manager.chat.entity.UserRole;
import com.manager.chat.services.AuthenticationService;
import com.manager.chat.services.impl.AuthenticationServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;



public class LoginController {

    private AuthenticationService authenticationService = null;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Label lblStatus;

    private Manager authenticatedManager = null;
    
    @FXML
    private void initialize(){
    	
        authenticationService = AuthenticationServiceImpl.getInstance();
        loadSavedManagerData();
    }



    private void loadSavedManagerData(){
        try {
            String fileData = new String(Files.readAllBytes(Paths.get("data.txt")));
            if(!fileData.isEmpty()) {
                JSONObject obj = new JSONObject(fileData);
                if(obj.has("username") && obj.has("password")) {
                    txtUsername.setText(obj.getString("username"));
                    txtPassword.setText(obj.getString("password"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveManagerData(Manager manager){
        try {
            JSONObject userObj = new JSONObject();
            userObj.put("username", manager.getUsername());
            userObj.put("password", manager.getPassword());
            Files.write(Paths.get("data.txt"), userObj.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param event -
     * @throws Exception -
     */
    public void actionLogin(ActionEvent event) throws Exception{

        if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()){
            lblStatus.setText("Please fill the following fields");
        }else {
            authenticatedManager = new Manager(txtUsername.getText(), txtPassword.getText(), UserRole.MANAGER);
        	validateUser(authenticatedManager);
        }
    }


    /**
     *
     * @param manager -
     */
    private void validateUser(Manager manager){
        authenticationService.validateUser(manager);
        int i = 50;
        while(i-- > 0){
            try {
                Thread.sleep(50);
                if(authenticationService.isUserValidated()){
                    saveManagerData(authenticatedManager);
                    Main.showMainScene();
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lblStatus.setText("Login or Password is incorrect");
    }


}
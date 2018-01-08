package com.manager.chat.controllers;

import com.manager.chat.Main;
import com.manager.chat.entity.*;
import com.manager.chat.services.AuthenticationService;
import com.manager.chat.services.ClientEndPointService;
import com.manager.chat.services.MessageService;
import com.manager.chat.services.UserService;
import com.manager.chat.services.impl.AuthenticationServiceImpl;
import com.manager.chat.services.impl.ClientEndpointServiceImpl;
import com.manager.chat.services.impl.MessageServiceImpl;
import com.manager.chat.services.impl.UserServiceImpl;
import com.manager.chat.view.ChatMessageCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ChatController {

    private MessageService messageService = null;
    private AuthenticationService authenticationService = null;
    private ClientEndPointService clientEndPointService = null;
    private UserContact selectedContact = null;
    private UserService userService = null;


    @FXML
    private ListView<UserContact> userListView;

    @FXML
    private ListView<Message> contactMessages;

    @FXML
    private Button btnSendMsg;

    @FXML
    private Button btnFileLoad;

    @FXML
    private TextArea textInputMsg;

    @FXML
    private AnchorPane contactMessageArea;
    
    @FXML
    private AnchorPane confirmUser;



    @FXML
    private void initialize(){

        authenticationService   = AuthenticationServiceImpl.getInstance();
        messageService          = MessageServiceImpl.getInstance();
        clientEndPointService   = ClientEndpointServiceImpl.getInstance();
        userService             = UserServiceImpl.getInstance();
        userListView.setItems(authenticationService.getAuthenticatedUser().getObservableContactList());
    }


    /**
     *
     * @param mouseEvent -
     */
    public void actionChatUserMouseClick(MouseEvent mouseEvent) {

        selectedContact = userListView.getSelectionModel().getSelectedItem();
        if(selectedContact == null){
            return;
        }
       	if(selectedContact.getContactUser().getUserStatus().equals(UserStatus.DISABLED)) {
       		contactMessageArea.setVisible(false);
       		confirmUser.setVisible(true);
       	}else {
       		confirmUser.setVisible(false);
       		contactMessageArea.setVisible(true);
	        contactMessageArea.setVisible(true);
	        showCurrentClientChat();
       	}
    }



    /**
     * ф-ция отрабатывает по клику на кнопку отправки сообщения
     * @param event -
     */
    public void actionSendMessage(ActionEvent event) {
        if ((textInputMsg.getText() != null && !textInputMsg.getText().isEmpty())) {
            Message newMsg = new Message();
            newMsg.setMessage(textInputMsg.getText());
            newMsg.setAddressee(authenticationService.getAuthenticatedUser());
            newMsg.setDestination(selectedContact.getContactUser());
            newMsg.setContactHistory(selectedContact.getContactHistory());
            //selectedContact.getContactHistory().addMessage(newMsg);
            //messageService.addMessage(newMsg);
            clientEndPointService.sendTextMessage(newMsg);
        }
        textInputMsg.clear();
    }

    
    /**
     * ф-ция отрабатывает по клику на кнопку отправки сообщения
     * @param event -
     */
    public void actionConfirmUser(ActionEvent event) {

        clientEndPointService.confirmNewContact(selectedContact);
    	Thread thread = new Thread(() -> {
            int i = 50;
            while(i-- > 0){
                try {
                    Thread.sleep(50);
                    if(selectedContact.getContactUser().getUserStatus().equals(UserStatus.ENABLED)){
                        actionChatUserMouseClick(null);
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    /**
     *
     * @param event -
     */
    public void actionSendFile(ActionEvent event) {
    	
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(Main.stage);

        if (file != null) {
            Message newMsg = new Message();
            MessageFile messageFile = new MessageFile();
            messageFile.setFile(file);
            try {
				byte[] fileData = Files.readAllBytes(Paths.get(file.toURI()));
				messageFile.setFileData(fileData);
				messageFile.setMessage(newMsg);
	            newMsg.setFile(messageFile);
                clientEndPointService.sendBinaryMessage(newMsg);
	            //messageService.addMessage(newMsg);
	            //showCurrentClientChat();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }


    /**
     *
     */
    private void showCurrentClientChat(){

    	if(selectedContact != null) {
    		contactMessages.setItems(selectedContact.getContactHistory().getObservableMessages());
            contactMessages.setCellFactory((ListView<Message> mess) -> new ChatMessageCell());
            contactMessages.refresh();
    	}
    }
}

package com.manager.chat.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import com.manager.chat.entity.*;
import com.manager.chat.services.MessageService;
import com.manager.chat.services.UserContactService;
import com.manager.chat.services.UserService;
import com.manager.chat.translator.impl.ManagerTranslator;
import com.manager.chat.translator.impl.UserTranslator;
import com.manager.chat.translator.impl.MessageTranslator;
import com.manager.chat.translator.impl.UserContactTranslator;
import org.json.JSONException;
import javax.websocket.*;
import com.manager.chat.services.AuthenticationService;
import com.manager.chat.services.ClientEndPointService;
import com.manager.chat.services.ContactHistoryService;

import org.json.JSONObject;



public class ClientEndpointServiceImpl extends Endpoint implements ClientEndPointService{


    
    private static ClientEndpointServiceImpl instance = null;
    //private static final String SERVER_URL = "ws://h2744859.stratoserver.net:8080/chat-server-application/chat";
    private static final String SERVER_URL = "ws://localhost:8080/chat";
    //private static final String SERVER_URL = "ws://192.168.43.227:8080/chat";

    private ChatBinaryMessageHandler chatBinaryMessageHandler;

    private AuthenticationService authenticationService = AuthenticationServiceImpl.getInstance();
    private UserContactService userContactService = UserContactServiceImpl.getInstance();
    private MessageTranslator messageTranslator = null;
    private UserTranslator userTranslator = null;
    private UserContactTranslator userContactTranslator = null;
    private MessageService messageService = null;
    private UserService userService = null;
    private ContactHistoryService contactHistoryService = null;
    private ManagerTranslator managerTranslator = null;
    private Session session = null;



    /**
     *
     * @return -
     */
    public static synchronized ClientEndpointServiceImpl getInstance(){
        if (instance == null)
            instance = new ClientEndpointServiceImpl();
        return instance;
    }


    /**
     *
     */
    private ClientEndpointServiceImpl(){
        chatBinaryMessageHandler = new ChatBinaryMessageHandler();
        messageService = MessageServiceImpl.getInstance();
        userTranslator = new UserTranslator();
        userService = UserServiceImpl.getInstance();
        messageTranslator = new MessageTranslator();
        managerTranslator = new ManagerTranslator();
        contactHistoryService = ContactHistoryServiceImpl.getInstance();
        userContactTranslator = new UserContactTranslator();
        openConnection();
    }


    private void openConnection(){
        try {
            ContainerProvider.getWebSocketContainer().connectToServer(this, new URI(SERVER_URL));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    @Override
    public void onClose(Session session, CloseReason closeReason) {
        super.onClose(session, closeReason);
        openConnection();
    }




    @Override
    public void onOpen(Session session, EndpointConfig arg1) {
        
        this.session = session;
        this.session.addMessageHandler(new ChatTextMessageHandler());
        this.session.addMessageHandler(chatBinaryMessageHandler);
        if(authenticationService.isUserAuthenticated() && !authenticationService.isUserValidated()) {
            validateUser(authenticationService.getAuthenticatedUser());
        }
    }




    /**
     * 
     * @param manager -
     */
    public void validateUser(Manager manager){
        if(session == null || !session.isOpen()){
            openConnection();
        }else{
            JSONObject requestObj = new JSONObject();
            requestObj.put("action", "authentication");
            requestObj.put("parameters", managerTranslator.toJSON(manager));
            sendTextData(requestObj.toString());
        }
    }



    /**
     * 
     * @param message -
     */
    public void sendTextMessage(Message message){
        JSONObject requestObj = new JSONObject();
        requestObj.put("action", "message");
        requestObj.put("parameters", messageTranslator.toJSON(message));
        sendTextData(requestObj.toString());
    }



    /**
     * 
     * @param message -
     */
    public void sendBinaryMessage(Message message){
        JSONObject requestObj = new JSONObject();
        requestObj.put("action", "fileSent");
        requestObj.put("parameters", messageTranslator.toJSON(message));
        sendBinaryData(ByteBuffer.wrap(message.getFile().getFileData()));
        sendTextData(requestObj.toString());
    }




    @Override
	public void confirmNewContact(UserContact user) {
        JSONObject requestObj = new JSONObject();
        requestObj.put("action", "contactConfirmation");
        requestObj.put("parameters", userContactTranslator.toJSON(user));
        sendTextData(requestObj.toString());
	}




    /**
     * sending textMessage
     * @param textMessage -
     */
    private void sendTextData(String textMessage){
        try {
            session.getBasicRemote().sendText(textMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    /**
     *
     * @param fileBytes -
     */
    private void sendBinaryData(ByteBuffer fileBytes){
        try {
            session.getBasicRemote().sendBinary(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    




    /**
     *  handler for text data
     */
    private class ChatTextMessageHandler implements MessageHandler.Whole<String>{


        @Override
        public void onMessage(String msgData) {
            try{
                JSONObject obj = new JSONObject(msgData);
                String action = obj.getString("action");
                JSONObject parameters = obj.getJSONObject("parameters");
                switch (action){
                    case "messageNotification":  actionHandleTextMessageNotification(parameters); break;
                    case "authentication":       actionAuthenticateUser(parameters); break;
                    case "fileSentConfirmation": actionHandleFileMessage(parameters); break;
                    case "contactNotification":  actionContactNotification(parameters); break;
                    case "contactConfirmation":  actionContactConfirmation(parameters); break;
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }



        
        

		/**
         *
         * @param authUser -
         */
        private void actionAuthenticateUser(JSONObject authUser) {
        	authenticationService.authenticateUser(managerTranslator.fromJSON(authUser));
        }



        



        /**
         * 
         * @param contactData -
         */
        private void actionContactNotification(JSONObject contactData) {

            userService.addUser(userTranslator.fromJSON(contactData.getJSONObject("user")));
            UserContact userContact = userContactService.addUserContact(userContactTranslator.fromJSON(contactData.getJSONObject("contact")));
            authenticationService.getAuthenticatedUser().addContact(userContact);
        }


        /**
         *
         * @param contactData -
         */
        private void actionContactConfirmation(JSONObject contactData) {

            ContactHistory contactHistory = new ContactHistory();
            contactHistory.setId(contactData.getInt("contactHistoryId"));
            contactHistoryService.addCotactHistory(contactHistory);
            UserContact userContact = userContactService.addUserContact(userContactTranslator.fromJSON(contactData.getJSONObject("contact")));
            authenticationService.getAuthenticatedUser().addContact(userContact);
            userContact.getContactUser().setUserStatus(UserStatus.ENABLED);
            userService.updateUser(userContact.getContactUser());
        }


        /**
         *
         * @param fileData -
         */
        private void actionHandleFileMessage(JSONObject fileData) {

            Message message = messageTranslator.fromJSON(fileData);
            MessageFile mf = new MessageFile();
            mf.setFileData(chatBinaryMessageHandler.binaryMessage.toByteArray());
            chatBinaryMessageHandler.binaryMessage.reset();
            message.setFile(mf);

            UserContact authContact = authenticationService.getAuthenticatedUser().findContactByUserName(message.getAddressee().getUsername());
            authContact.getContactHistory().addMessage(messageService.addMessage(message));
        }


        /**
         *
         * @param messageObj - 
         */
        private void actionHandleTextMessageNotification(JSONObject messageObj) {

        	Message message = messageService.addMessage(messageTranslator.fromJSON(messageObj));
            UserContact authContact = authenticationService.getAuthenticatedUser().findContactByUserName(message.getAddressee().getUsername());
            authContact.getContactHistory().addMessage(message);
        }
        


    }


    /**
     *  handler for binary data
     */
    private class ChatBinaryMessageHandler implements MessageHandler.Whole<ByteBuffer>{


        private ByteArrayOutputStream binaryMessage;

        ChatBinaryMessageHandler(){
            binaryMessage = new ByteArrayOutputStream();
        }

        @Override
		public void onMessage(ByteBuffer data) {
			while(data.hasRemaining()) {
                binaryMessage.write(data.get());
	        }
		}
    }




	




	



}

package com.manager.chat.entity;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CONTACT_HISTORY")
public class ContactHistory extends BaseEntity {




    @Column(name = "CREATED_TIME", nullable = false)
    private Date createdTime;



    @OneToMany(mappedBy = "contactHistory")
    private List<Message> messages = new ArrayList<>();


    @Transient
    private ObservableList<Message> observableMessages = null;//FXCollections.observableArrayList();



    public ContactHistory() {
        super();
    }



    public ContactHistory(List<Message> messages) {
        super();
        this.messages = messages;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }



    public List<Message> getMessages() {
        return messages;
    }


    public void setMessages(List<Message> messages) {
        this.messages = messages;
        
        if(observableMessages == null) {
    		observableMessages = FXCollections.observableArrayList();
    	}
        observableMessages.clear();
        observableMessages.addAll(messages);
    }

    public void addMessage(Message message){
        messages.add(message);
        if(observableMessages == null) {
    		observableMessages = FXCollections.observableArrayList();
    		observableMessages.addAll(getMessages());
    	}else {
    		observableMessages.add(message);
    	}
        
        
    }

    public ObservableList<Message> getObservableMessages() {
    	if(observableMessages == null) {
    		observableMessages = FXCollections.observableArrayList();
    		observableMessages.addAll(getMessages());
    	}
        return observableMessages;
    }

}
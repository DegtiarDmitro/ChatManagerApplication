package com.manager.chat.entity;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_ROLE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(UserRole.BUYER)
public class User extends BaseEntity{

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "USER_ROLE", insertable = false, updatable = false)
    private String role;

    
    @Column(name = "USER_STATUS")
    private String userStatus;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "USER_CONTACT",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ID"))
    private List<UserContact> contactList = new ArrayList<>();


    @Transient
    private ObservableList<UserContact> observableContactList = null;


    public User() {
        super();
    }

    public User(String userName, String password, String role) {
        super();
        this.username = userName;
        this.password = password;
        this.role = role;
    }

    public User(String userName, String email, String password, String phone, String role) {
        super();
        this.username = userName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }



    public List<UserContact> getContactList() {
        return contactList;
    }

    public void setContactList(List<UserContact> contactList) {
        this.contactList = contactList;
        if(observableContactList == null) {
        	observableContactList = FXCollections.observableArrayList();
    		observableContactList.addAll(getContactList());
        }else {
        	observableContactList.clear();
        	observableContactList.addAll(contactList);
        }
        
    }

    public void addContact(UserContact contact){
        contactList.add(contact);
        if(observableContactList == null) {
        	observableContactList = FXCollections.observableArrayList();
    		observableContactList.addAll(getContactList());
        }else {
        	observableContactList.add(contact);
        }
        
    }

    public UserContact getContact(){
    	if(!contactList.isEmpty()) {
    		return contactList.get(0);
    	}
        return null;
    }
    
    
    public UserContact findContactByUserName(String contactUserName) {
    	
    	
    	if(observableContactList != null) {
    		for(UserContact userContact: contactList) {
    			if(userContact.getContactUser().getUsername().equals(contactUserName)) {
    				return userContact;
    			}
    		}
    	}
    	
    	return null;
    }
    

    public ObservableList<UserContact> getObservableContactList() {
    	
    	if(observableContactList == null) {
    		observableContactList = FXCollections.observableArrayList();
    		observableContactList.addAll(getContactList());    	}
    	
        return observableContactList;
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (!username.equals(user.username)) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (!password.equals(user.password)) return false;
        return role.equals(user.role);
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + username.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + password.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }
    
    

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}

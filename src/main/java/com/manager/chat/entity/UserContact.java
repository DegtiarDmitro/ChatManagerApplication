package com.manager.chat.entity;




import javax.persistence.*;


@Entity
@Table(name = "USER_CONTACT")
public class UserContact extends BaseEntity {


    @ManyToOne
    @JoinColumn(name="CONTACT_HISTORY_ID")
    private ContactHistory contactHistory;


    @ManyToOne
    @JoinColumn(name="USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="CONTACT_USER_ID", nullable = false)
    private User contactUser;



    public UserContact() {
        super();
    }

    public ContactHistory getContactHistory() {
        return contactHistory;
    }

    public void setContactHistory(ContactHistory contactHistory) {
        this.contactHistory = contactHistory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public User getContactUser() {
        return contactUser;
    }

    public void setContactUser(User contactUser) {
        this.contactUser = contactUser;
    }


    @Override
    public String toString() {
        return contactUser.getUsername();
    }



}

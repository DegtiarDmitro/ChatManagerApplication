package com.manager.chat.entity;


import javax.persistence.*;

@Entity
@DiscriminatorValue(UserRole.MANAGER)
public class Manager extends User {

	public Manager() {
		super();
		setUserStatus(UserStatus.ENABLED);
	}


	public Manager(String userName, String password, String role) {
		super(userName, password, role);
	}
}

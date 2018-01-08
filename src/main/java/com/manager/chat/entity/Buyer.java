package com.manager.chat.entity;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(UserRole.BUYER)
public class Buyer extends User {
}

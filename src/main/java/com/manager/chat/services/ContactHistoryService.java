package com.manager.chat.services;

import com.manager.chat.entity.ContactHistory;

public interface ContactHistoryService{

    ContactHistory getContactHistory(int id);
    void addCotactHistory(ContactHistory contactHistory);
}

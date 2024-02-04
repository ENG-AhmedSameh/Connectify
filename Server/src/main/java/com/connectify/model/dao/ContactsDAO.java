package com.connectify.model.dao;


import com.connectify.model.entities.Contacts;
import com.connectify.model.entities.User;

import java.util.List;

public interface ContactsDAO extends DAO<Contacts, String> {
    List<User> getContactsList(String PhoneNumber);
}
public interface ContactsDAO extends DAO<Contacts, String> {
    boolean areAlreadyFriends(String userPhone, String phone2);
}


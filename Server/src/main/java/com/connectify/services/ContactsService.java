package com.connectify.services;

import com.connectify.mapper.UserMapper;
import com.connectify.model.dao.ContactsDAO;
import com.connectify.model.dao.UserDAO;
import com.connectify.model.dao.impl.ContactsDAOImpl;
import com.connectify.model.dao.impl.UserDAOImpl;
import com.connectify.model.entities.User;

public class ContactsService {

    public boolean areAlreadyFriends(String userPhone, String friendPhone) {
        ContactsDAO contactsDAO = new ContactsDAOImpl();
        return contactsDAO.areAlreadyFriends(userPhone, friendPhone);
    }
}

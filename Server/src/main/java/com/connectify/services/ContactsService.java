package com.connectify.services;

import com.connectify.Server;
import com.connectify.mapper.UserMapper;
import com.connectify.model.dao.ContactsDAO;
import com.connectify.model.dao.UserDAO;
import com.connectify.model.dao.impl.ContactsDAOImpl;
import com.connectify.model.dao.impl.UserDAOImpl;
import com.connectify.model.entities.User;

import java.rmi.RemoteException;

public class ContactsService {

    public boolean areAlreadyFriends(String userPhone, String friendPhone) {
        ContactsDAO contactsDAO = new ContactsDAOImpl();
        return contactsDAO.areAlreadyFriends(userPhone, friendPhone);
    }

    public void notifyContacts(String phoneNumber, String title, String body){
        ContactsDAO contactsDAO = new ContactsDAOImpl();
        var contacts = contactsDAO.getContactsList(phoneNumber);
        var connectedUsers = Server.getConnectedUsers();
        for(var contact : contacts){
            var connectedContact = connectedUsers.get(contact.getPhoneNumber());
            if(connectedContact != null){
                try {
                    connectedContact.receiveNotification(title, body);
                } catch (RemoteException e) {
                    System.err.println("Remote Exception: " + e.getMessage());
                }
            }
        }
    }
}

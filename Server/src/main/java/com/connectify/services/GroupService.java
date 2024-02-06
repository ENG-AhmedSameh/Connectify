package com.connectify.services;

import com.connectify.Server;
import com.connectify.dto.ContactsDTO;
import com.connectify.model.dao.ContactsDAO;
import com.connectify.model.dao.GroupDAO;
import com.connectify.model.dao.impl.ContactsDAOImpl;
import com.connectify.model.dao.impl.GroupDAOImpl;
import com.connectify.model.entities.Group;

import java.rmi.RemoteException;
import java.util.List;

public class GroupService {

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

    public boolean createGroup(List<ContactsDTO> contactsDTOS, String groupName, String groupDescription, byte[] image) {
        GroupDAO groupDAO = new GroupDAOImpl();
        return groupDAO.createGroup(contactsDTOS, groupName, groupDescription, image);
    }
}

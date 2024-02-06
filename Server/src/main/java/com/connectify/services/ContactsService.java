package com.connectify.services;

import com.connectify.Server;
import com.connectify.model.dao.ContactsDAO;
import com.connectify.model.dao.impl.ContactsDAOImpl;
import com.connectify.model.enums.Status;

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
    public void updateUserModeAtContactsToOffline(String phoneNumber){
        ContactsDAO contactsDAO = new ContactsDAOImpl();
        var contacts = contactsDAO.getContactsList(phoneNumber);
        var connectedUsers = Server.getConnectedUsers();
        for(var contact : contacts){
            var connectedContact = connectedUsers.get(contact.getPhoneNumber());
            if(connectedContact != null){
                try {
                    connectedContact.updateContactModeToOffline(phoneNumber);
                } catch (RemoteException e) {
                    System.err.println("Remote Exception: " + e.getMessage());
                }
            }
        }
    }

    public void updateUserStatusAtContacts(String phoneNumber, Status userStatus) {
        ContactsDAO contactsDAO = new ContactsDAOImpl();
        var contacts = contactsDAO.getContactsList(phoneNumber);
        var connectedUsers = Server.getConnectedUsers();
        for(var contact : contacts){
            var connectedContact = connectedUsers.get(contact.getPhoneNumber());
            if(connectedContact != null){
                try {
                    connectedContact.updateContactStatus(phoneNumber,userStatus);
                } catch (RemoteException e) {
                    System.err.println("Remote Exception: " + e.getMessage());
                }
            }
        }
    }
}

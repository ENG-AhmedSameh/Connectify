package com.connectify.utils;

import com.connectify.Interfaces.ConnectedUser;

import java.io.File;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CurrentUser extends UnicastRemoteObject implements ConnectedUser, Serializable {

    private final String phoneNumber;

    public CurrentUser(String phoneNumber) throws RemoteException {
        super();
        this.phoneNumber = phoneNumber;
    }

    public CurrentUser(String phoneNumber, File profileImage, String bio) throws RemoteException {
        super();
        this.phoneNumber = phoneNumber;
    }
    @Override
    public void receiveAnnouncement(String announcement) throws RemoteException {

    }

    public String getPhoneNumber() throws RemoteException {
        return phoneNumber;
    }
}

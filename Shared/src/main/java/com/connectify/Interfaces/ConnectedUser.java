package com.connectify.Interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConnectedUser extends Remote {
    void receiveAnnouncement(String announcement) throws RemoteException;

    String getPhoneNumber() throws RemoteException;
}

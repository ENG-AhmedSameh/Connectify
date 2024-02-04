package com.connectify.Interfaces;

import com.connectify.dto.IncomingFriendInvitationResponse;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConnectedUser extends Remote {
    void receiveAnnouncement(String announcement) throws RemoteException;

    String getPhoneNumber() throws RemoteException;

    void receiveFriendRequest(IncomingFriendInvitationResponse friendInvitation) throws RemoteException;

    void showNotification(String title, String message) throws RemoteException;
}

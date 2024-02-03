package com.connectify.utils;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.controller.IncomingFriendRequestController;
import com.connectify.dto.IncomingFriendInvitationResponse;
import com.connectify.loaders.IncomingFriendRequestCardLoader;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CurrentUser extends UnicastRemoteObject implements ConnectedUser, Serializable {

    private final String phoneNumber;

    public CurrentUser(String phoneNumber) throws RemoteException {
        super();
        this.phoneNumber = phoneNumber;
    }
    @Override
    public void receiveAnnouncement(String announcement) throws RemoteException {

    }

    @Override
    public String getPhoneNumber() throws RemoteException {
        return phoneNumber;
    }

    @Override
    public void receiveFriendRequest(IncomingFriendInvitationResponse friendInvitation) throws RemoteException {
        AnchorPane newFriendRequestCard = IncomingFriendRequestCardLoader
                .loadNewIncomingFriendRequestCardPane(
                        friendInvitation.getName(), friendInvitation.getPhoneNumber(),
                        friendInvitation.getPicture(), friendInvitation.getInvitationId());

        ObservableList<AnchorPane> friendRequestList = IncomingFriendRequestController.getFriendRequestList();
        friendRequestList.add(newFriendRequestCard);
    }

}

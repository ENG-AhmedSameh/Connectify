package com.connectify.utils;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.controller.IncomingFriendRequestController;
import com.connectify.dto.IncomingFriendInvitationResponse;
import com.connectify.loaders.IncomingFriendRequestCardLoader;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Notifications;

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

        Platform.runLater(() -> {
            friendRequestList.add(newFriendRequestCard);
        });

        String title = "New Friend Request";
        String message = friendInvitation.getName() + " has sent you a friend request.";
        try {
            showNotification(title, message);
        } catch (RemoteException e) {
            System.err.println("Error receive Friend Request. case:" + e.getMessage());
        }
    }

    @Override
    public void showNotification(String title, String message) throws RemoteException {
        Platform.runLater(() -> {
            Notifications.create()
                    .title(title)
                    .text(message)
                    .darkStyle()
                    .threshold(3, Notifications.create().title("Collapsed Notification"))
                    .showInformation();
        });
    }


}

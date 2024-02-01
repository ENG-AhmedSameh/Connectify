package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class OptionsController {

    @FXML
    private ImageView alContactsButtonImageView;

    @FXML
    private Button allChatsButton;

    @FXML
    private ImageView allChatsButtonImageView;

    @FXML
    private Button allContactsButton;

    @FXML
    private AnchorPane homeScreenOptionsAnchorPane;

    @FXML
    private Button inviteFriendButton;

    @FXML
    private ImageView inviteFriendButtonImageView;

    @FXML
    private Button userProfileButton;

    @FXML
    private Button userProfileButton1;

    @FXML
    private ImageView userProfileImageView;

    @FXML
    private ImageView userProfileImageView1;

    @FXML
    void chatsHandler(ActionEvent event) {

    }

    @FXML
    void contactsHandler(ActionEvent event) {

    }

    @FXML
    void inviteHandler(ActionEvent event) {

    }

    @FXML
    void logoutHandler(ActionEvent event) {
        try {
            ServerAPI server = (ServerAPI) Client.getRegistry().lookup("server");
            server.unregisterConnectedUser(Client.getConnectedUser());
            Client.updateUserCredentials("false");
            Client.setConnectedUser(null);
        } catch (RemoteException e) {
            System.err.println("RemoteException: " + e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("NotBoundException: " + e.getMessage());
        }
        StageManager.getInstance().switchToLogin();
    }

    @FXML
    void userProfileHandler(ActionEvent event) {

    }

}

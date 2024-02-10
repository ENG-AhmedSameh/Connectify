package com.connectify.controller;

import com.connectify.utils.CurrentUser;
import com.connectify.utils.PropertiesManager;
import com.connectify.utils.RemoteManager;
import com.connectify.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
    private Button incomingFriendRequestButton;

    @FXML
    private ImageView incomingFriendRequestImageView;

    @FXML
    void chatsHandler(ActionEvent event) {
        StageManager.getInstance().switchToChats();
    }

    @FXML
    void contactsHandler(ActionEvent event) {
        StageManager.getInstance().switchToContacts();
    }

    @FXML
    void inviteHandler(ActionEvent event) {
        StageManager.getInstance().switchToAddFriend();
    }

    @FXML
    void logoutHandler(ActionEvent event) {
        try {
            RemoteManager.getInstance().logout(CurrentUser.getInstance());
        } catch (RemoteException e) {
            System.err.println("Error while logging out: " + e.getMessage());
        }
        PropertiesManager.getInstance().setUserCredentials("false");
        CurrentUser.resetAllData();
        RemoteManager.reset();
        StageManager.getInstance().resetHomeScene();
        StageManager.getInstance().switchToLogin();
    }

    @FXML
    void userProfileHandler(ActionEvent event) {
        StageManager.getInstance().switchToProfile();
    }

    @FXML
    void incomingFriendRequestHandler(ActionEvent event) {
        StageManager.getInstance().switchToIncomingFriendRequest();
    }

}

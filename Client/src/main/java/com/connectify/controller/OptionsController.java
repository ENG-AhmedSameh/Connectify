package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.ContactsDTO;
import com.connectify.loaders.AllContactsPaneLoader;
import com.connectify.loaders.LogoLoader;
import com.connectify.model.entities.User;
import com.connectify.loaders.ViewLoader;
import com.connectify.utils.ChatManager;
import com.connectify.utils.ChatManagerFactory;
import com.connectify.utils.ChatPaneFactory;
import com.connectify.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

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
        StageManager stageManager =StageManager.getInstance();
        Scene scene = stageManager.getSceneMap().get("home");
        BorderPane mainPane = (BorderPane) scene.getRoot();
        BorderPane centerPane=(BorderPane) mainPane.getCenter();
        centerPane.setLeft(AllContactsPaneLoader.loadAllContactsAnchorPane());
        centerPane.setCenter(LogoLoader.loadLogoAnchorPane());
    }

    @FXML
    void inviteHandler(ActionEvent event) {
        StageManager.getInstance().switchToAddFriend();
    }

    @FXML
    void logoutHandler(ActionEvent event) {
        try {
            ServerAPI server = (ServerAPI) Client.getRegistry().lookup("server");
            server.unregisterConnectedUser(Client.getConnectedUser());
            server.logout(Client.getConnectedUser().getPhoneNumber());
            Client.updateUserCredentials("false");
            Client.setConnectedUser(null);
            ChatPaneFactory.clearChats();
            AllChatsPaneController.clearChatsCardList();
            ChatManagerFactory.clearChatManagersMap();
            StageManager.getInstance().resetHomeScene();
        } catch (RemoteException e) {
            System.err.println("RemoteException: " + e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("NotBoundException: " + e.getMessage());
        }
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

package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.FriendToAddResponse;
import com.connectify.loaders.AddFriendCardLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddFriendController implements Initializable {

    @FXML
    private TextField newContactPhoneSearchTextField;

    @FXML
    private AnchorPane addFriendAnchorPane;

    @FXML
    private Button cancelButton;

    @FXML
    private ScrollPane seaerchContactsScrollPane;

    @FXML
    private VBox searchContactsVBox;

    @FXML
    private Button sendInvitationsButton;

    @FXML
    private Button serchButton;

    private ServerAPI server;
    private static String currentUserPhone = "01143414035";
    List<FriendToAddResponse> friendToAddResponseList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            server = (ServerAPI) Client.registry.lookup("server");
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("NotBoundException: " + e.getMessage());
        }
    }
    @FXML
    void searchButtonHandler(ActionEvent event) {
        try {
            FriendToAddResponse friendToAddResponse = server.getFriendToAdd(newContactPhoneSearchTextField.getText());
            if (friendToAddResponse != null && isUnique(friendToAddResponse.getPhoneNumber())) {
                addFriendRequestCard(friendToAddResponse.getName(), friendToAddResponse.getPhoneNumber(),
                        friendToAddResponse.getPicture());
            }
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
        }
    }

    @FXML
    void sendInvitationsButtonHandler(ActionEvent event) {
        for (FriendToAddResponse friendToAddResponse : friendToAddResponseList) {
            try {
                server.sendInvitation(currentUserPhone, friendToAddResponse.getPhoneNumber());
            } catch (RemoteException e) {
                System.err.println("Remote Exception: " + e.getMessage());
            }
        }
    }

    @FXML
    void cancelButtonHandler(ActionEvent event) {

    }

    private boolean isUnique(String phoneNumber) {
        for (FriendToAddResponse friendToAddResponse : friendToAddResponseList) {
            if (friendToAddResponse.getPhoneNumber().equals(phoneNumber)) {
                return false;
            }
        }
        return true;
    }

    public void addFriendRequestCard(String name, String phone, byte[] picture){
        AnchorPane addFriendCard = AddFriendCardLoader.loadNewAddFriendCardPane(name, phone, picture);
        searchContactsVBox.getChildren().add(addFriendCard);
    }
}

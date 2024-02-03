package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.loaders.AddFriendCardLoader;
import com.connectify.loaders.ChatCardLoader;
import com.connectify.loaders.IncomingFriendRequestCardLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class IncomingFriendRequestController implements Initializable {

    @FXML
    private AnchorPane incomingFriendRequestAnchorPane;

    @FXML
    private ListView<AnchorPane> allInvitationsListView;
    ObservableList<AnchorPane> friendRequestList = FXCollections.observableArrayList();

    private ServerAPI server;
    private static String currentUserPhone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            server = (ServerAPI) Client.getRegistry().lookup("server");
            currentUserPhone = "+20" + Client.getConnectedUser().getPhoneNumber();
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("NotBoundException: " + e.getMessage());
        }

        initializeListView();
        loadAllIncomingFriendRequest();
    }

    private void initializeListView(){
        allInvitationsListView.setItems(friendRequestList);
        setListViewCellFactory();
    }

    private void setListViewCellFactory() {
        allInvitationsListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<AnchorPane> call(ListView<AnchorPane> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(AnchorPane item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            setGraphic(item);
                        }
                    }
                };
            }
        });
    }

    private void loadAllIncomingFriendRequest() {

    }


    public void addIncomingFriendRequestCard(String name, String phone, byte[] picture){
        AnchorPane addFriendCard = IncomingFriendRequestCardLoader
                .loadNewIncomingFriendRequestCardPane(name, phone, picture);
        friendRequestList.add(addFriendCard);
    }
}

package com.connectify.controller;

import com.connectify.dto.IncomingFriendInvitationResponse;
import com.connectify.loaders.IncomingFriendRequestCardLoader;
import com.connectify.utils.CurrentUser;
import com.connectify.utils.RemoteManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class IncomingFriendRequestController implements Initializable {

    @FXML
    private AnchorPane incomingFriendRequestAnchorPane;

    @FXML
    private ListView<AnchorPane> allInvitationsListView;
    private static ObservableList<AnchorPane> friendRequestList = FXCollections.observableArrayList();


    private static String currentUserPhone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            currentUserPhone = CurrentUser.getInstance().getPhoneNumber();
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
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
        List<IncomingFriendInvitationResponse> incomingFriendRequests = RemoteManager.getInstance().getIncomingFriendRequests(currentUserPhone);
        for (IncomingFriendInvitationResponse  response: incomingFriendRequests) {
            addIncomingFriendRequestCard(response.getName(), response.getPhoneNumber(), response.getPicture(), response.getInvitationId());
        }
    }

    public void addIncomingFriendRequestCard(String name, String phone, byte[] picture, int invitationId){
        AnchorPane addFriendCard = IncomingFriendRequestCardLoader
                .loadNewIncomingFriendRequestCardPane(name, phone, picture, invitationId);
        friendRequestList.add(addFriendCard);
    }

    public static ObservableList<AnchorPane> getFriendRequestList() {
        return friendRequestList;
    }
}

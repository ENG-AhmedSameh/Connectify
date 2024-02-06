package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.FriendToAddResponse;
import com.connectify.loaders.AddFriendCardLoader;
import com.connectify.model.entities.Invitations;
import com.connectify.utils.RemoteManager;
import com.connectify.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.Remote;
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

    private static String currentUserPhone;
    List<FriendToAddResponse> friendToAddResponseList = new ArrayList<>();
    private String txtFieldsOriginalStyle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtFieldsOriginalStyle = newContactPhoneSearchTextField.getStyle();
        try {
            currentUserPhone = Client.getConnectedUser().getPhoneNumber();
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
        }
    }
    @FXML
    void searchButtonHandler(ActionEvent event) {
        String newContactPhone = newContactPhoneSearchTextField.getText();
        FriendToAddResponse friendToAddResponse = RemoteManager.getInstance().getFriendToAdd(newContactPhone);

        if (isEligibleForFriendAddition(friendToAddResponse)) {
            handleFriendAddition(friendToAddResponse);
        } else {
            handleInvalidFriendAddition();
        }
    }

    private void handleFriendAddition(FriendToAddResponse friendToAddResponse) {
        friendToAddResponseList.add(friendToAddResponse);
        addFriendRequestCard(friendToAddResponse.getName(), friendToAddResponse.getPhoneNumber(),
                friendToAddResponse.getPicture());

        newContactPhoneSearchTextField.clear();
        newContactPhoneSearchTextField.setStyle(txtFieldsOriginalStyle);
    }

    private void handleInvalidFriendAddition() {
        newContactPhoneSearchTextField.setTooltip(hintText("Invalid phone number"));
        newContactPhoneSearchTextField.setStyle("-fx-border-color: red;");
    }

    private boolean isEligibleForFriendAddition(FriendToAddResponse friendToAddResponse) {
        return friendToAddResponse != null && !friendToAddResponse.getPhoneNumber().equals(currentUserPhone) &&
                isUnique(friendToAddResponse.getPhoneNumber()) && !isInvitationSent() && !areAlreadyFriends();
    }

    private boolean areAlreadyFriends() {
        return RemoteManager.getInstance().areAlreadyFriends(currentUserPhone ,newContactPhoneSearchTextField.getText());
    }

    private Tooltip hintText(String text) {
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: rgba(241,241,241,1); -fx-text-fill: black; -fx-background-radius: 4; -fx-border-radius: 4; -fx-opacity: 1.0;");
        tooltip.setAutoHide(false);
        tooltip.setMaxWidth(300);
        tooltip.setWrapText(true);
        tooltip.setText(text);
        //tooltip.setGraphic(image);
        return tooltip;
    }

    private boolean isInvitationSent() {
        return RemoteManager.getInstance().isInvitationSent(currentUserPhone ,newContactPhoneSearchTextField.getText());
    }

    @FXML
    void sendInvitationsButtonHandler(ActionEvent event) {
        for (FriendToAddResponse friendToAddResponse : friendToAddResponseList) {
            boolean result = RemoteManager.getInstance().sendInvitation(currentUserPhone, friendToAddResponse.getPhoneNumber());
            System.out.println("Send invitation result: " + result);
        }
        searchContactsVBox.getChildren().clear();
        StageManager.getInstance().switchToChats();
    }

    @FXML
    void cancelButtonHandler(ActionEvent event) {
        searchContactsVBox.getChildren().clear();
        StageManager.getInstance().switchToChats();
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

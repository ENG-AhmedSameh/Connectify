package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.ChatCardsInfoDTO;
import com.connectify.loaders.ChatCardLoader;
import com.connectify.utils.CurrentUser;
import com.connectify.utils.RemoteManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class IncomingFriendRequestCardController implements Initializable {

    @FXML
    private Button acceptFriendRequestButton;

    @FXML
    private Button cancelFriendRequestButton;

    @FXML
    private HBox messageHBox;

    @FXML
    private Circle senderImage;

    @FXML
    private Label senderNameLabel;

    @FXML
    private Label senderPhoneNumberLabel;

    private String name;
    private byte[] pictureBytes;
    private String phone;
    private int invitationId;
    private static String currentUserPhone;

    public IncomingFriendRequestCardController() {

    }

    public IncomingFriendRequestCardController(String name, byte[] pictureBytes, String phone, int invitationId) {
        this.name = name;
        this.pictureBytes = pictureBytes;
        this.phone = phone;
        this.invitationId = invitationId;
        try {
            currentUserPhone = Client.getConnectedUser().getPhoneNumber();
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCardName(name);
        setCardPhone(phone);
        setImage(pictureBytes);
    }

    private void setImage(byte[] pictureBytes) {
        Image image = null;
        if (pictureBytes == null) {
            image = new Image(String.valueOf(ProfileController.class.getResource("/images/profile.png")));
        } else {
            image = new Image(new ByteArrayInputStream(pictureBytes));
        }
        senderImage.setFill(new ImagePattern(image));
    }

    private void setCardPhone(String phone) {
        senderPhoneNumberLabel.setText(phone);
    }

    private void setCardName(String name) {
        senderNameLabel.setText(name);
    }

    @FXML
    void handleAcceptPressed(ActionEvent event) {
        boolean friendRequestAccepted = RemoteManager.getInstance().acceptFriendRequest(invitationId);
        if (friendRequestAccepted) {
            ObservableList<AnchorPane> friendRequestList = IncomingFriendRequestController.getFriendRequestList();
            friendRequestList.removeIf(this::isControllerMatch);
        }
        ChatCardsInfoDTO chat = null;
        try {
            chat = RemoteManager.getInstance().getUserLastChatCardInfo(Client.getConnectedUser().getPhoneNumber());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        AnchorPane chatCard = ChatCardLoader.loadChatCardAnchorPane(chat.getChatID(),chat.getUnreadMessagesNumber(),chat.getName(),chat.getPicture(),chat.getLastMessage(),chat.getTimestamp());
        CurrentUser.getAllChatsController().getChatsPanesList().add(chatCard);
    }

    @FXML
    void handleCancelPressed(ActionEvent event) {
        boolean friendRequestCanceled = RemoteManager.getInstance().cancelFriendRequest(invitationId);

        if (friendRequestCanceled) {
            ObservableList<AnchorPane> friendRequestList = IncomingFriendRequestController.getFriendRequestList();

            friendRequestList.removeIf(this::isControllerMatch);
        }
    }

    private boolean isControllerMatch(AnchorPane anchorPane) {
        IncomingFriendRequestCardController controller = (IncomingFriendRequestCardController) anchorPane.getUserData();
        return controller == this;
    }

    public int getInvitationId() {
        return invitationId;
    }

}

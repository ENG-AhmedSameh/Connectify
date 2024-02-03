package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
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

    String name;
    byte[] pictureBytes;
    String phone;

    public IncomingFriendRequestCardController() {

    }

    public IncomingFriendRequestCardController(String name, byte[] pictureBytes, String phone) {
        this.name = name;
        this.pictureBytes = pictureBytes;
        this.phone = phone;
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

    }

    @FXML
    void handleCancelPressed(ActionEvent event) {

    }
}

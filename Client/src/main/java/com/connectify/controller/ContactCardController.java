package com.connectify.controller;

import com.connectify.model.entities.User;
import com.connectify.utils.ImageConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactCardController implements Initializable {
    User user;
    public ContactCardController(User user)
    {
        this.user=user;
    }

    @FXML
    private Label contactNameLabel;

    @FXML
    private Circle contactPicture;

    @FXML
    private Label contactPhoneNumberLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactNameLabel.setText(user.getName());
        contactPicture.setFill(ImageConverter.convertBytesToImagePattern(user.getPicture()));
        contactPhoneNumberLabel.setText(user.getPhoneNumber());
        System.out.println(contactNameLabel.getText());
    }

    public void paneOnClicked(MouseEvent mouseEvent) {
        System.out.println("Contact");
    }

}

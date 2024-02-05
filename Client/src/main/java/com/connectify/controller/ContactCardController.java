package com.connectify.controller;

import com.connectify.model.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
    private ImageView contactPictureImageView;

    @FXML
    private Label contactPhoneNumberLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactNameLabel.setText(user.getName());
//        contactPictureImageView.setImage(user.getPicture());
        contactPhoneNumberLabel.setText(user.getPhoneNumber());
    }

    public void paneOnClicked(MouseEvent mouseEvent) {
        System.out.println("Contact");
    }

}

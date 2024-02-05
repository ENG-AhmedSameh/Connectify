package com.connectify.controller;

import com.connectify.dto.ContactsDTO;
import com.connectify.model.entities.User;
import com.connectify.utils.ImageConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ChooseContactCardController implements Initializable {

    @FXML
    private Label contactNameLabel;

    @FXML
    private Label contactPhoneNumberLabel;

    @FXML
    private CheckBox selectedContactCheckBox;

    @FXML
    private Circle userImageCircle;

    private ContactsDTO contact;

    public ChooseContactCardController(ContactsDTO contact)
    {
        this.contact = contact;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactNameLabel.setText(contact.getName());
        userImageCircle.setFill(ImageConverter.convertBytesToImagePattern(contact.getPicture()));
        contactPhoneNumberLabel.setText(contact.getPhoneNumber());
    }

}

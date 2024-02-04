package com.connectify.loaders;

import com.connectify.controller.ContactCardController;
import com.connectify.model.entities.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ContactCardLoader {
    public static AnchorPane loadContactCardAnchorPane(User contact){
        ContactCardController contactCardController =new ContactCardController(contact);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ContactCardLoader.class.getResource("/views/ContactCardPane.fxml"));
        fxmlLoader.setController(contactCardController);
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

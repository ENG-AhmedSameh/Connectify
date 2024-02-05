package com.connectify.loaders;

import com.connectify.controller.ChooseContactCardController;
import com.connectify.controller.ContactCardController;
import com.connectify.controller.IncomingFriendRequestCardController;
import com.connectify.dto.ContactsDTO;
import com.connectify.model.entities.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ChooseContactCardLoader {
    public static AnchorPane loadChooseContactCardAnchorPane(ContactsDTO contact){
        FXMLLoader fxmlLoader = new FXMLLoader();
        ChooseContactCardController chooseContactCardController =new ChooseContactCardController(contact);
        fxmlLoader.setLocation(ChooseContactCardLoader.class.getResource("/views/ChooseContactCardPane.fxml"));
        fxmlLoader.setController(chooseContactCardController);
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            anchorPane.setUserData(chooseContactCardController);
            return anchorPane;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

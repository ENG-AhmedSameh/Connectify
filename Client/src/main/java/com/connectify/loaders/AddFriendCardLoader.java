package com.connectify.loaders;

import com.connectify.controller.AddFriendCardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AddFriendCardLoader {

    public static AnchorPane loadNewAddFriendCardPane(String name, String phone, byte[] picture) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        AddFriendCardController controller = new AddFriendCardController(name, phone, picture);
        fxmlLoader.setLocation(ChatCardLoader.class.getResource("/views/AddFriendPane.fxml"));
        fxmlLoader.setController(controller);
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

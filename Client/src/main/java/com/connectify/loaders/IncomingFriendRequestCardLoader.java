package com.connectify.loaders;

import com.connectify.controller.AddFriendCardController;
import com.connectify.controller.IncomingFriendRequestCardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class IncomingFriendRequestCardLoader {
    public static AnchorPane loadNewIncomingFriendRequestCardPane(String name, String phone, byte[] picture, int invitationId) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        IncomingFriendRequestCardController controller = new IncomingFriendRequestCardController(name, picture, phone, invitationId);
        fxmlLoader.setLocation(IncomingFriendRequestCardController.class.getResource("/views/IncomingFriendRequestCardPane.fxml"));
        fxmlLoader.setController(controller);
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            anchorPane.setUserData(controller);
            return anchorPane;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

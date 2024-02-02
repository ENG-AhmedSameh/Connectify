package com.connectify.loaders;

import com.connectify.controller.AllChatsPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AllChatsPaneLoader {
    public static AnchorPane loadAllChatsAnchorPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AllChatsPaneLoader.class.getResource("/views/AllChatsPane.fxml"));
        AllChatsPaneController controller = new AllChatsPaneController();
        fxmlLoader.setController(controller);
        try {
            AnchorPane pane = fxmlLoader.load();
            return pane;
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.connectify.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AllChatsPaneLoader {
    public static AnchorPane loadAllChatsAnchorPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AllChatsPaneLoader.class.getResource("/views/AllChatsPane.fxml"));
        try {
            return fxmlLoader.load();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

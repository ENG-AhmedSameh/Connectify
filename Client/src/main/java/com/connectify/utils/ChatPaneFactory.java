package com.connectify.utils;
import com.connectify.loaders.ChatLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChatPaneFactory {


    private static final Map<Integer, BorderPane> paneCache = new HashMap<>();

    public static BorderPane getChatPane(int id, String name, Image image) {

        if (paneCache.containsKey(id)) {
            return paneCache.get(id);

        }
        BorderPane newPane = ChatLoader.loadChatPane(id,name,image);
        paneCache.put(id, newPane);
        return newPane;
    }

}

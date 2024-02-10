package com.connectify.loaders;

import com.connectify.controller.StyleChatTabController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class StyleChatTabLoader {
    public static HBox loadChatStyleHBox(TextArea textArea){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(SignUpLoader.class.getResource("/views/StyleChatTab.fxml"));
        StyleChatTabController controller  = new StyleChatTabController(textArea);
        fxmlLoader.setController(controller);
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

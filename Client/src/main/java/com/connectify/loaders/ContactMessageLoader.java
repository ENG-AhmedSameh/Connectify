package com.connectify.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ContactMessageLoader {
    public static AnchorPane loadContactMessage(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ContactMessageLoader.class.getResource("/views/ContactMessage.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

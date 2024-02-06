package com.connectify.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ChooseContactsGroupPaneLoader {
    public static AnchorPane loadChooseContactsGroupAnchorPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ChooseContactsGroupPaneLoader.class.getResource("/views/ChooseContactsGroupPane.fxml"));
        try {
            return fxmlLoader.load();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.connectify.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import java.io.IOException;
public class ViewLoader {
    public Pane getMainPane() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/SignUpPane.fxml"));
        Pane mainPane = null;
        try {
            mainPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mainPane;
    }
}

package com.connectify.controller;

import com.connectify.loaders.ViewLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AllChatsPaneController implements Initializable {

    ViewLoader loader = ViewLoader.getInstance();

    @FXML
    private TextField chatSearchTextField;

    @FXML
    private AnchorPane allChatsAnchorPane;

    @FXML
    private ScrollPane allChatsScrollPane;

    @FXML
    private VBox allChatsVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0;i<10;i++)
            addChatOnChatPane();
        System.out.println("done");
    }

    public void addChatOnChatPane(){

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/ChatCardPane.fxml"));
        Parent chatCard;
        try {
            chatCard = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        allChatsVBox.getChildren().add(chatCard);
    }

}

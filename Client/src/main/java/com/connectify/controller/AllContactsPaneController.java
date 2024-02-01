package com.connectify.controller;

import com.connectify.loaders.ChatCardLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AllContactsPaneController implements Initializable {

    @FXML
    private TextField ContactSearchTextField;

    @FXML
    private AnchorPane allContactsAnchorPane;

    @FXML
    private ScrollPane allContactsScrollPane;

    @FXML
    private VBox allContactsVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0;i<10;i++)
            addContactOnContactsPane();
        System.out.println("done");
    }

    public void addContactOnContactsPane(){
        AnchorPane chatCard = ChatCardLoader.loadChatCardAnchorPane();
        allContactsVBox.getChildren().add(chatCard);
    }

}

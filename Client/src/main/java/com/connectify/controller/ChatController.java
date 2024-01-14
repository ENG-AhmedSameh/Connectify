package com.connectify.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class ChatController{


    @FXML
    private Circle chatImage;

    @FXML
    private Text chatName;

    @FXML
    private VBox chatPane;

    @FXML
    private Text membersCount;

    @FXML
    private Circle statusCircle;

    @FXML
    private VBox chatWindow;

    @FXML
    void attachmentHandler(MouseEvent event) {
        System.out.println("Attachment button pressed");
    }

    @FXML
    void sendHandler(MouseEvent event) {
        System.out.println("Send button pressed");
    }

}

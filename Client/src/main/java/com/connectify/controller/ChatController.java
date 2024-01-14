package com.connectify.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class ChatController {

    @FXML
    private Circle chatImage;

    @FXML
    private Text chatName;

    @FXML
    private VBox chatWindow;

    @FXML
    private Text membersCount;

    @FXML
    private TextField sendBox;

    @FXML
    private Circle statusCircle;

    @FXML
    void attachmentHandler(MouseEvent event) {
        System.out.println("Attachment pressed");
    }

    @FXML
    void openHTMLEditorHandler(MouseEvent event) {
        System.out.println("Edit pressed");
    }

    @FXML
    void sendHandler(MouseEvent event) {
        System.out.println("send pressed");
    }

}

package com.connectify.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class ReceivedMessageHBoxController implements Initializable {

    @FXML
    private Circle imageCircle;

    @FXML
    private Text messageContentText;

    @FXML
    private HBox userMessageBox;

    @FXML
    private TextFlow contentTextFlow;

    Image senderImage;
    String messageContent;

    public ReceivedMessageHBoxController(Image senderImage, String messageContent) {
        this.senderImage = senderImage;
        this.messageContent = messageContent;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //this.imageCircle.setFill((Paint) senderImage);
        this.messageContentText.setText(messageContent);
    }
}

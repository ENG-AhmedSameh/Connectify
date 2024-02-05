package com.connectify.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class GroupMessageHBoxController{

    @FXML
    private TextFlow contentTextFlow;

    @FXML
    private Circle imageCircle;

    @FXML
    private Text messageContentText;

    @FXML
    private VBox messageElementsVBox;

    @FXML
    private Label messageTimeLabel;

    @FXML
    private Label senderNameLabel;

    @FXML
    private HBox userMessageBox;

    public void setSameSenderMessageStyle(String messageContent,Timestamp messageTime){
        Platform.runLater(()->{
            messageElementsVBox.getChildren().remove(senderNameLabel);
            imageCircle.setVisible(false);
            contentTextFlow.setStyle("-fx-background-radius: 10 10 0 0");
            setMessageContent(messageContent);
            setMessageTime(messageTime);
        });
    }
    public void setDifferentSenderMessageStyle(String senderName,byte[] senderImage ,String messageContent, Timestamp messageTime){
        Platform.runLater(()->{
            setMessageContent(messageContent);
            setMessageTime(messageTime);
            setSenderName(senderName);
//            setSenderImage(senderImage);
        });
    }


    private void setSenderName(String senderName) {
        senderNameLabel.setText(senderName);
    }


    private void setSenderImage(Image senderImage) {
        //imageCircle.setFill();
    }


    private void setMessageContent(String messageContent) {
        messageContentText.setText(messageContent);
    }



    private void setMessageTime(Timestamp messageTime) {
        messageTimeLabel.setText(messageTime.toLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
    }
}

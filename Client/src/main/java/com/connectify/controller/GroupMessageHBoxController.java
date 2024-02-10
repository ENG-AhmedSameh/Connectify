package com.connectify.controller;

import com.connectify.utils.ImageConverter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class GroupMessageHBoxController{

    @FXML
    private TextFlow contentTextFlow;

    @FXML
    private Circle imagePicture;

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
            imagePicture.setVisible(false);
            contentTextFlow.setStyle("-fx-background-radius: 10 10 0 0");
            setMessageContent(messageContent);
            setMessageTime(messageTime);
        });
    }
    public void setDifferentSenderMessageStyle(String senderName,byte[] senderImage ,String messageContent, Timestamp messageTime,String messageStyle){
        String newMessageStyle = messageStyle.replace("-fx-text-fill","-fx-fill");
        Platform.runLater(()->{
            setMessageContent(messageContent);
            setMessageTime(messageTime);
            setSenderName(senderName);
            setSenderImage(senderImage);
            messageContentText.setStyle(newMessageStyle);
        });
    }


    private void setSenderName(String senderName) {
        senderNameLabel.setText(senderName);
    }


    private void setSenderImage(byte[] senderImage) {
        imagePicture.setFill(ImageConverter.convertBytesToImagePattern(senderImage));
    }


    private void setMessageContent(String messageContent) {
        messageContentText.setText(messageContent);
    }



    private void setMessageTime(Timestamp messageTime) {
        messageTimeLabel.setText(messageTime.toLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
    }
}

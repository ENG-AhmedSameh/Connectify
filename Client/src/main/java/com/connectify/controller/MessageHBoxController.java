package com.connectify.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MessageHBoxController implements Initializable {

    @FXML
    private Circle imageCircle;

    @FXML
    private Text messageContentText;

    @FXML
    private HBox userMessageBox;

    @FXML
    private TextFlow contentTextFlow;

    @FXML
    private Label messageTimeLabel;

    String messageContent;
    Timestamp messageTimestamp;

    String messageStyle;

    public MessageHBoxController(String messageContent, Timestamp messageTimestamp,String style) {
        this.messageContent = messageContent;
        this.messageTimestamp = messageTimestamp;
        messageStyle = style;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.messageContentText.setText(messageContent);
        this.messageTimeLabel.setText(messageTimestamp.toLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
        messageStyle = messageStyle.replace("-fx-text-fill","-fx-fill");
        messageContentText.setStyle(messageStyle);
//        System.out.println(messageStyle);
    }

}

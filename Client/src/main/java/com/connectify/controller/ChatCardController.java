package com.connectify.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatCardController implements Initializable {

    @FXML
    private Label chatNameTextField;

    @FXML
    private ImageView chatPictureImageView;

    @FXML
    private Label lastMessageLabel;

    @FXML
    private Label lastMessageTimeLabel;

    @FXML
    private HBox messageHBox;

    @FXML
    private Label nChatUnreadMessagesLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void paneOnClicked(MouseEvent mouseEvent) {
        System.out.println("ya raaaaaaaab");
    }

//    @FXML
//    private void onMouseEnteredHandler(ActionEvent event){
//        messageHBox.setStyle("-fx-background-color: lightgreen;");
//    }


}

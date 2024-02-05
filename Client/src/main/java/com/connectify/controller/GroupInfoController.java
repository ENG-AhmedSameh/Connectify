package com.connectify.controller;

import com.connectify.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class GroupInfoController {

    @FXML
    private Button BackButton;

    @FXML
    private TextField GroupDescriptionTextField;

    @FXML
    private AnchorPane GroupInfoAnchorPane;

    @FXML
    private Button chooseGroupImageButton;

    @FXML
    private Button createButton;

    @FXML
    private TextField groupNameTextField;

    @FXML
    private Circle userImageCircle;

    @FXML
    void BackHandler(ActionEvent event) {
        StageManager.getInstance().switchToChooseContactsGroupPane();
    }

    @FXML
    void chooseGroupImageHandler(ActionEvent event) {

    }

    @FXML
    void createHandler(ActionEvent event) {

        StageManager.getInstance().switchToChats();
    }

}

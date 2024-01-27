package com.connectify.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;



public class titleBarController{

    public HBox titleBarHBox;
    @FXML
    private ImageView closeButton;

    @FXML
    private ImageView minimizeButton;

    @FXML
    private HBox dragBar;


    private static double xOffset = 0;
    private static double yOffset = 0;



    @FXML
    void closeButtonHandler(MouseEvent event) {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
//        alert.setHeaderText("Exit ?");
//        alert.showAndWait();
//        if (alert.getResult() == ButtonType.YES) {
            System.exit(0);
//        }
    }

    @FXML
    void minimizeButtonHandler(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void onMousePressedHandler(MouseEvent mouseEvent) {
        xOffset = mouseEvent.getSceneX();
        yOffset = mouseEvent.getSceneY();
    }

    public void onMouseDraggedHandler(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - xOffset);
        stage.setY(mouseEvent.getScreenY() - yOffset);
    }

    public void maximizeButtonHandler(MouseEvent event) {
        Stage stage = (Stage) titleBarHBox.getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }
}


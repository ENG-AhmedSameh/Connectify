package com.connectify.controller;

import com.connectify.Client;
import com.connectify.utils.CurrentUser;
import com.connectify.utils.RemoteManager;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.rmi.RemoteException;


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

    private static Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    private static double screenMaxWidth = visualBounds.getWidth();
    private static double screenMaxHeight = visualBounds.getHeight();
    private static double lastXPosition;
    private static double lastYPosition;
    private static double lastWidth;
    private static double lastHeight;
    boolean maximized = false;



    @FXML
    void closeButtonHandler(MouseEvent event) {
        System.exit(0);
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
        if(!maximized){
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        }
    }

    public void maximizeButtonHandler(MouseEvent event) {
        Stage stage = (Stage) titleBarHBox.getScene().getWindow();
        if(!maximized){
            lastXPosition =stage.getX();
            lastYPosition = stage.getY();
            lastWidth = stage.getWidth();
            lastHeight = stage.getHeight();
            stage.setWidth(screenMaxWidth);
            stage.setHeight(screenMaxHeight);
            stage.setX(0);
            stage.setY(0);
            maximized = true;
        }else{
            stage.setWidth(lastWidth);
            stage.setHeight(lastHeight);
            stage.setX(lastXPosition);
            stage.setY(lastYPosition);
            maximized =false;
        }
    }
}


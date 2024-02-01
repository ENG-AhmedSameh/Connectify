package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
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



    @FXML
    void closeButtonHandler(MouseEvent event) {
        if(Client.getConnectedUser() != null){
            try {
                ServerAPI server = (ServerAPI) Client.getRegistry().lookup("server");
                server.unregisterConnectedUser(Client.getConnectedUser());
            } catch (RemoteException e) {
                System.err.println("Remote Exception: " + e.getMessage());
            } catch (NotBoundException e) {
                System.err.println("NotBoundException: " + e.getMessage());
            }
        }
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
        stage.setX(mouseEvent.getScreenX() - xOffset);
        stage.setY(mouseEvent.getScreenY() - yOffset);
    }

    public void maximizeButtonHandler(MouseEvent event) {
        Stage stage = (Stage) titleBarHBox.getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }
}


package com.connectify.fxmlcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    BorderPane adminPane;
    @FXML
    private Label statusLabel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusLabel.setText("Offline");
        statusLabel.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
    }


    @FXML
    public void onStartClick(){
        System.out.println("Starting Server...");
        statusLabel.setText("Online");
        statusLabel.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @FXML
    public void onStopClick(){
        System.out.println("Stopping Server...");
        statusLabel.setText("Offline");
        statusLabel.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}

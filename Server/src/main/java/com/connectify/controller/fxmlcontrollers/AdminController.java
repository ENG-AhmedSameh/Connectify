package com.connectify.controller.fxmlcontrollers;

import com.connectify.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private BorderPane adminPane;
    @FXML
    private Label statusLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;

    private boolean isRunning;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusLabel.setText("Online");
        statusLabel.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(5), Insets.EMPTY)));
        isRunning = true;
    }


    @FXML
    public void onStartClick(){
        if(isRunning)
            return;
        Server.powerUp();
        statusLabel.setText("Online");
        statusLabel.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(5), Insets.EMPTY)));
        isRunning = true;
    }

    @FXML
    public void onStopClick(){
        if(!isRunning)
            return;
        Server.powerDown();
        statusLabel.setText("Offline");
        statusLabel.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(5), Insets.EMPTY)));
        isRunning = false;
    }
}

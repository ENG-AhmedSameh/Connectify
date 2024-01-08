package com.connectify.fxmlcontrollers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;


public class ServerController implements Initializable{
    @FXML
    private BorderPane mainPane;

    @FXML
    private HBox dragBar;

    private static double xOffset = 0;
    private static double yOffset = 0;


    private BorderPane adminPane;

    private BorderPane announcementPane;

    private BorderPane statisticsPane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPane.setCenter(adminPane);
        dragBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        dragBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    public void setAdminPane(BorderPane adminPane) {
        this.adminPane = adminPane;
    }

    public void setAnnouncementPane(BorderPane announcementPane) {
        this.announcementPane = announcementPane;
    }

    public void setStatisticsPane(BorderPane statisticsPane) {
        this.statisticsPane = statisticsPane;
    }


    @FXML
    public void onAdminClick(){
        mainPane.setCenter(adminPane);
    }

    @FXML
    public void onAnnouncementClick(){
        mainPane.setCenter(announcementPane);
    }

    @FXML
    public void onStatisticsClick(){
        mainPane.setCenter(statisticsPane);
    }

    @FXML
    public void onExitClick(){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? The Server will stop if it is running", ButtonType.YES, ButtonType.NO);
            alert.setHeaderText("Exit ?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                System.exit(0);
            }
    }

    @FXML
    public void minimizeButtonHandler(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void closeButtonHandler(MouseEvent event) {
        onExitClick();
    }

}

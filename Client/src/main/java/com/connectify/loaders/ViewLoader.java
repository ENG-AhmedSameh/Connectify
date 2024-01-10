package com.connectify.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;
public class ViewLoader {


    BorderPane mainBorderPane;
    AnchorPane logoAnchorPane;
    AnchorPane signUpAnchorPane;

    HBox titleBarHBox;

    private ViewLoader(){
        loadMainPane();
        //loadTitleBar();
        loadLogoPane();
        loadSignUpPane();
    }

    private void loadMainPane() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/MainPane.fxml"));
        try {
            mainBorderPane =fxmlLoader.load();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTitleBar(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/titleBarPane.fxml"));
        try {
            mainBorderPane =fxmlLoader.load();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadSignUpPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/SignUpPane.fxml"));
        try {
            signUpAnchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadLogoPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/logoPane.fxml"));
        try {
            logoAnchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private static final ViewLoader viewLoader = new ViewLoader();
    public static ViewLoader getInstance() {
        return viewLoader;
    }

    public AnchorPane getSignUpAnchorPane() {
        return signUpAnchorPane;
    }


    public BorderPane getMainBorderPane() {
        return mainBorderPane;
    }

    public AnchorPane getLogoAnchorPane() {
        return logoAnchorPane;
    }

    public HBox getTitleBarHBox() {
        return titleBarHBox;
    }
}

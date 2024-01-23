package com.connectify.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;

import java.io.IOException;
public class ViewLoader {


    BorderPane mainBorderPane;
    AnchorPane logoAnchorPane;
    AnchorPane signUpAnchorPane;
    AnchorPane loginAnchorPane;

    AnchorPane homeScreenOptionsPane;

    AnchorPane allChatsAnchorPane;

    HBox titleBarHBox;
    AnchorPane chatCardHBox;
    BorderPane chatWindow;

    private ViewLoader(){
    }
    private static ViewLoader viewLoader;;
    public static ViewLoader getInstance(){
        if(viewLoader==null)
            viewLoader = new ViewLoader();
        return viewLoader;
    }



    private void loadAllChatsAnchorPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/AllChatsPane.fxml"));
        try {
            allChatsAnchorPane =fxmlLoader.load();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadMainBorderPane() {
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
            titleBarHBox =fxmlLoader.load();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadSignUpAnchorPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/SignUpPane.fxml"));
        try {
            signUpAnchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadLoginAnchorPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/LoginPane.fxml"));
        try {
            loginAnchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadLogoAnchorPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/logoPane.fxml"));
        try {
            logoAnchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadChatPane() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/Chat.fxml"));
        try {
            chatWindow = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadHomeScreenOptionsPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/HomeScreenOptionsPane.fxml"));
        try {
            homeScreenOptionsPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadUserMessage(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/UserMessage.fxml"));
        try {
            logoAnchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadChatCardHBox(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/ChatCardPane.fxml"));
        try {
            chatCardHBox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadContactMessage(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/ContactMessage.fxml"));
        try {
            logoAnchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void switchFromSignUpToHomeScreen(){
        if(homeScreenOptionsPane==null)
            loadHomeScreenOptionsPane();
        if(allChatsAnchorPane==null)
            loadAllChatsAnchorPane();
        mainBorderPane.setLeft(homeScreenOptionsPane);
        BorderPane newCenterPane = new BorderPane();
        newCenterPane.setLeft(allChatsAnchorPane);
        newCenterPane.setCenter(logoAnchorPane);
        mainBorderPane.setCenter(newCenterPane);
    }
    public void switchFromLoginToSignUpScreen(){
        if(signUpAnchorPane==null)
            loadSignUpAnchorPane();
        GridPane centerPane =(GridPane)mainBorderPane.getCenter();
        centerPane.getChildren().remove(loginAnchorPane);
        centerPane.add(signUpAnchorPane, 1, 0);
    }

    public void switchFromSignUpToLogin() {
        if(loginAnchorPane==null)
            loadLoginAnchorPane();
        GridPane centerPane =(GridPane)mainBorderPane.getCenter();
        centerPane.getChildren().remove(signUpAnchorPane);
        centerPane.add(loginAnchorPane, 1, 0);
    }

    public void switchToLogin(){
        if(titleBarHBox==null)
            loadTitleBar();
        if(logoAnchorPane==null)
            loadLogoAnchorPane();
        if(loginAnchorPane==null)
            loadLoginAnchorPane();
        if(mainBorderPane==null)
            loadMainBorderPane();
        GridPane centerPane =(GridPane)mainBorderPane.getCenter();
        centerPane.add(logoAnchorPane,0,0);
        centerPane.add(loginAnchorPane,1,0);
        mainBorderPane.setTop(titleBarHBox);
        mainBorderPane.setCenter(centerPane);
    }
    public BorderPane getMainBorderPane(){
        return mainBorderPane;
    }

}

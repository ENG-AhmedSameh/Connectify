package com.connectify.loaders;

import javafx.scene.Scene;
import javafx.scene.layout.*;

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
    BorderPane chatsHomeScreenCenterPane = new BorderPane();

    private ViewLoader(){
    }
    private static ViewLoader viewLoader;;
    public static ViewLoader getInstance(){
        if(viewLoader==null)
            viewLoader = new ViewLoader();
        return viewLoader;
    }


    public void switchToHomeScreen(String userID){
        if(homeScreenOptionsPane==null)
            homeScreenOptionsPane=HomeScreenOptionsLoader.loadHomeScreenOptionsAnchorPane();
        if(allChatsAnchorPane==null)
            allChatsAnchorPane=AllChatsPaneLoader.loadAllChatsAnchorPane();
        mainBorderPane.setLeft(homeScreenOptionsPane);

        chatsHomeScreenCenterPane.setLeft(allChatsAnchorPane);
        chatsHomeScreenCenterPane.setCenter(logoAnchorPane);
        mainBorderPane.setCenter(chatsHomeScreenCenterPane);
    }
    public void switchFromLoginToSignUpScreen(){
        if(signUpAnchorPane==null)
            signUpAnchorPane=SignUpLoader.loadSignUpAnchorPane();
        GridPane centerPane =(GridPane)mainBorderPane.getCenter();
        centerPane.getChildren().remove(loginAnchorPane);
        centerPane.add(signUpAnchorPane, 1, 0);
    }

    public void switchFromSignUpToLogin() {
        if(loginAnchorPane==null)
            loginAnchorPane=LoginLoader.loadLoginAnchorPane();
        GridPane centerPane =(GridPane)mainBorderPane.getCenter();
        centerPane.getChildren().remove(signUpAnchorPane);
        centerPane.add(loginAnchorPane, 1, 0);
    }

    public void switchToLogin(){
        if(titleBarHBox==null)
            titleBarHBox = TitleBarLoader.loadTitleBarHBox();
        if(logoAnchorPane==null)
            logoAnchorPane=LogoLoader.loadLogoAnchorPane();
        if(loginAnchorPane==null)
            loginAnchorPane=LoginLoader.loadLoginAnchorPane();
        if(mainBorderPane==null)
            mainBorderPane=MainPaneLoader.loadMainBorderPane();
        GridPane centerPane =(GridPane)mainBorderPane.getCenter();
        centerPane.add(logoAnchorPane,0,0);
        centerPane.add(loginAnchorPane,1,0);
        mainBorderPane.setTop(titleBarHBox);
        mainBorderPane.setCenter(centerPane);
    }

    public BorderPane getMainBorderPane(){
        return mainBorderPane;
    }

    public void switchToChat(BorderPane chatPane){

        chatsHomeScreenCenterPane.setCenter(chatPane);
    }
    public void switchToChat(BorderPane chatPane, Scene currentScene){

        BorderPane homePane= (BorderPane) currentScene.getRoot();
        BorderPane chatsPane =(BorderPane)homePane.getCenter();
        chatsPane.setCenter(chatPane);
    }

}

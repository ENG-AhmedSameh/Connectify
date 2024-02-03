package com.connectify.utils;

import com.connectify.Client;
import com.connectify.loaders.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class StageManager {

    private static StageManager instance;

    private final Stage stage;

    private final Map<String, Scene> sceneMap;


    private StageManager(){
        stage = Client.getPrimaryStage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinWidth(750);
        stage.setMinHeight(500);
        sceneMap = new HashMap<>();
    }


    public static StageManager getInstance(){
        if(instance == null){
            instance = new StageManager();
        }
        return instance;
    }

    public void switchToLogin(){
        if(sceneMap.get("login") == null)
            sceneMap.put("login", createLoginScene());
        Scene scene = sceneMap.get("login");
        stage.setScene(scene);
    }

    public void switchToSignUp(){
        if(sceneMap.get("signup") == null)
            sceneMap.put("signup", createSignUpScene());
        Scene scene = sceneMap.get("signup");
        stage.setScene(scene);
    }

    public void switchToSecondSignUp(){
        if(sceneMap.get("secondSignUp") == null)
            sceneMap.put("secondSignUp", createSecondSignUpScene());
        Scene scene = sceneMap.get("secondSignUp");
        stage.setScene(scene);
    }


    public void switchToHome(){
        if(sceneMap.get("home") == null)
            sceneMap.put("home", createHomeScene());
        Scene scene = sceneMap.get("home");
        stage.setScene(scene);
    }

    private Scene createLoginScene(){
        BorderPane mainPane = new BorderPane();
        GridPane centerPane = new GridPane();
        AnchorPane logoPane = LogoLoader.loadLogoAnchorPane();
        AnchorPane loginPane = LoginLoader.loadLoginAnchorPane();
        centerPane.add(logoPane,0,0);
        centerPane.add(loginPane,1,0);
        HBox titleBar = TitleBarLoader.loadTitleBarHBox();
        mainPane.setTop(titleBar);
        mainPane.setCenter(centerPane);
        return new Scene(mainPane);
    }

    private Scene createSignUpScene(){
        BorderPane mainPane = new BorderPane();
        GridPane centerPane = new GridPane();
        AnchorPane logoPane = LogoLoader.loadLogoAnchorPane();
        AnchorPane signUpPane = SignUpLoader.loadSignUpAnchorPane();
        centerPane.add(logoPane,0,0);
        centerPane.add(signUpPane,1,0);
        HBox titleBar = TitleBarLoader.loadTitleBarHBox();
        mainPane.setTop(titleBar);
        mainPane.setCenter(centerPane);
        return new Scene(mainPane);
    }

    private Scene createSecondSignUpScene(){
        BorderPane mainPane = new BorderPane();
        AnchorPane logoPane = LogoLoader.loadLogoAnchorPane();
        AnchorPane secondSignUpPane = ImageBioLoader.loadImageBioAnchorPane();
        HBox titleBar = TitleBarLoader.loadTitleBarHBox();
        mainPane.setTop(titleBar);
        mainPane.setCenter(secondSignUpPane);
        mainPane.setLeft(logoPane);
        return new Scene(mainPane);
    }

    private Scene createHomeScene(){
        BorderPane mainPane = new BorderPane();
        HBox titleBar = TitleBarLoader.loadTitleBarHBox();
        BorderPane centerPane = new BorderPane();
        AnchorPane optionsPane = HomeScreenOptionsLoader.loadHomeScreenOptionsAnchorPane();
        AnchorPane chatsPane = AllChatsPaneLoader.loadAllChatsAnchorPane();
        AnchorPane logoPane = LogoLoader.loadLogoAnchorPane();
        centerPane.setLeft(chatsPane);
        centerPane.setCenter(logoPane);
        mainPane.setLeft(optionsPane);
        mainPane.setTop(titleBar);
        mainPane.setCenter(centerPane);
        return new Scene(mainPane);
    }

    public void resetHomeScene() {
        sceneMap.remove("home");
    }
}

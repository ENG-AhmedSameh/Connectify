package com.connectify.utils;

import com.connectify.Client;
import com.connectify.loaders.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class StageManager {

    private static StageManager instance;

    private final Stage stage;

    private final Map<String, Scene> sceneMap;
    private AnchorPane chatsPane;
    private AnchorPane logoPane;
    private AnchorPane incomingFriendRequestPane;


    private StageManager(){
        stage = Client.getPrimaryStage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
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
        BorderPane mainPane =(BorderPane)scene.getRoot();
        mainPane.setPrefSize(stage.getWidth(),stage.getHeight());
        stage.setScene(scene);
    }

    public void switchToSignUp(){
        if(sceneMap.get("signup") == null)
            sceneMap.put("signup", createSignUpScene());
        Scene scene = sceneMap.get("signup");
        BorderPane mainPane =(BorderPane)scene.getRoot();
        mainPane.setPrefSize(stage.getWidth(),stage.getHeight());
        stage.setScene(scene);
        //Platform.runLater(() -> stage.setScene(scene));
    }

    public void switchToSecondSignUp(){
        if(sceneMap.get("secondSignUp") == null)
            sceneMap.put("secondSignUp", createSecondSignUpScene());
        Scene scene = sceneMap.get("secondSignUp");
        BorderPane mainPane =(BorderPane)scene.getRoot();
        mainPane.setPrefSize(stage.getWidth(),stage.getHeight());
        stage.setScene(scene);
        //Platform.runLater(() -> stage.setScene(scene));
    }


    public void switchToHome(){
        try {
            System.out.println(CurrentUser.getInstance().getPhoneNumber());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if(sceneMap.get("home") == null)
            sceneMap.put("home", createHomeScene());
        Scene scene = sceneMap.get("home");
        BorderPane mainPane =(BorderPane)scene.getRoot();
        mainPane.setPrefSize(stage.getWidth(),stage.getHeight());
        stage.onCloseRequestProperty().set(e -> {
            try {
                RemoteManager.getInstance().logout(CurrentUser.getInstance());
            } catch (RemoteException ex) {
                System.err.println("Remote Exception: " + ex.getMessage());
            }
            System.exit(0);
        });
        stage.setScene(scene);
        //Platform.runLater(() -> stage.setScene(scene));
    }

    public void switchFromProfileEditorToHome(){
        Scene scene = sceneMap.get("home");
        BorderPane mainPane = (BorderPane) scene.getRoot();
        BorderPane centerPane = (BorderPane) mainPane.getCenter();
        centerPane.setCenter(logoPane);
        centerPane.setLeft(chatsPane);
    }

    public void switchToChats(){
        Scene scene = sceneMap.get("home");
        BorderPane mainPane = (BorderPane) scene.getRoot();
        BorderPane centerPane = (BorderPane) mainPane.getCenter();
        AnchorPane logoPane = LogoLoader.loadLogoAnchorPane();
        centerPane.setCenter(logoPane);
        centerPane.setLeft(chatsPane);
    }

    public void switchToChooseContactsGroupPane(){
        Scene scene = sceneMap.get("home");
        BorderPane mainPane = (BorderPane) scene.getRoot();
        BorderPane centerPane = (BorderPane) mainPane.getCenter();
        AnchorPane chooseContactsGroupAnchorPane = ChooseContactsGroupPaneLoader.loadChooseContactsGroupAnchorPane();
        centerPane.setCenter(logoPane);
        centerPane.setLeft(chooseContactsGroupAnchorPane);
    }

    public void switchToProfile(){
        Scene scene = sceneMap.get("home");
        BorderPane mainPane = (BorderPane) scene.getRoot();
        BorderPane centerPane = (BorderPane) mainPane.getCenter();
        centerPane.setCenter(ProfileLoader.loadProfileAnchorPane());
        AnchorPane logoPane = LogoLoader.loadLogoAnchorPane();
        logoPane.setStyle("-fx-background-color: #17212b");
        centerPane.setLeft(logoPane);
    }
    public void switchToContacts(){
        Scene scene = sceneMap.get("home");
        BorderPane mainPane = (BorderPane) scene.getRoot();
        BorderPane centerPane=(BorderPane) mainPane.getCenter();
        centerPane.setLeft(AllContactsPaneLoader.loadAllContactsAnchorPane());
        centerPane.setCenter(null);
        AnchorPane logoPane = LogoLoader.loadLogoAnchorPane();
        centerPane.setCenter(logoPane);
    }
    public void openContactChat(){

    }

    public void switchToProfileEditor(){
        Scene scene = sceneMap.get("home");
        BorderPane mainPane = (BorderPane) scene.getRoot();
        BorderPane centerPane = (BorderPane) mainPane.getCenter();
        centerPane.setCenter(ProfileEditorLoader.loadProfileEditorAnchorPane());
    }
    public void switchToAddFriend(){
        Scene scene = sceneMap.get("home");
        BorderPane mainPane = (BorderPane) scene.getRoot();
        BorderPane centerPane = (BorderPane) mainPane.getCenter();
        centerPane.setLeft(AddFriendLoader.loadAddFriendAnchorPane());
        AnchorPane logoPane = LogoLoader.loadLogoAnchorPane();
        centerPane.setCenter(logoPane);
    }

    public void switchToIncomingFriendRequest(){
        Scene scene = sceneMap.get("home");
        BorderPane mainPane = (BorderPane) scene.getRoot();
        BorderPane centerPane = (BorderPane) mainPane.getCenter();
        incomingFriendRequestPane = IncomingFriendRequestLoader.loadIncomingFriendRequestAnchorPane();
        centerPane.setLeft(incomingFriendRequestPane);
        AnchorPane logoPane = LogoLoader.loadLogoAnchorPane();
        centerPane.setCenter(logoPane);
    }

    private Scene createLoginScene(){
        BorderPane mainPane = MainPaneLoader.loadMainBorderPane();
        GridPane centerPane = (GridPane) mainPane.getCenter();
        AnchorPane logoPane = LogoLoader.loadLogoAnchorPane();
        AnchorPane loginPane = LoginLoader.loadLoginAnchorPane();
        centerPane.add(logoPane,0,0);
        centerPane.add(loginPane,1,0);
        HBox titleBar = TitleBarLoader.loadTitleBarHBox();
        mainPane.setTop(titleBar);
        mainPane.setCenter(centerPane);
        mainPane.setPrefSize(stage.getWidth(),stage.getHeight());
        mainPane.setStyle("-fx-border-color: #17212b; -fx-border-width:1");
        return new Scene(mainPane);
    }

    private Scene createSignUpScene(){
        BorderPane mainPane = MainPaneLoader.loadMainBorderPane();
        GridPane centerPane = (GridPane) mainPane.getCenter();
        AnchorPane logoPane = LogoLoader.loadLogoAnchorPane();
        AnchorPane signUpPane = SignUpLoader.loadSignUpAnchorPane();
        centerPane.add(logoPane,0,0);
        centerPane.add(signUpPane,1,0);
        HBox titleBar = TitleBarLoader.loadTitleBarHBox();
        mainPane.setTop(titleBar);
        mainPane.setCenter(centerPane);
        mainPane.setPrefSize(stage.getWidth(),stage.getHeight());
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
        mainPane.setPrefSize(stage.getWidth(),stage.getHeight());
        return new Scene(mainPane);
    }

    private Scene createHomeScene(){
        BorderPane mainPane = new BorderPane();
        HBox titleBar = TitleBarLoader.loadTitleBarHBox();
        BorderPane centerPane = new BorderPane();
        AnchorPane optionsPane = HomeScreenOptionsLoader.loadHomeScreenOptionsAnchorPane();
        chatsPane = AllChatsPaneLoader.loadAllChatsAnchorPane();
        logoPane = LogoLoader.loadLogoAnchorPane();
        centerPane.setLeft(chatsPane);
        centerPane.setCenter(logoPane);
        mainPane.setLeft(optionsPane);
        mainPane.setTop(titleBar);
        mainPane.setCenter(centerPane);
        mainPane.setPrefSize(stage.getWidth(),stage.getHeight());
        return new Scene(mainPane);
    }

    public void resetHomeScene() {
        sceneMap.remove("home");
    }
    public Map<String, Scene> getSceneMap() {
        return sceneMap;
    }

    public void switchToGroupInfo() {
        Scene scene = sceneMap.get("home");
        BorderPane mainPane = (BorderPane) scene.getRoot();
        BorderPane centerPane = (BorderPane) mainPane.getCenter();
        AnchorPane logoPane = LogoLoader.loadLogoAnchorPane();
        AnchorPane groupInfoPane = GroupInfoPaneLoader.loadGroupInfoAnchorPane();
        centerPane.setCenter(logoPane);
        centerPane.setLeft(groupInfoPane);
    }
}

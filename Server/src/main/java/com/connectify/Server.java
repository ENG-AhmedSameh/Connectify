package com.connectify;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.controller.ServerController;
import com.connectify.loaders.ViewLoader;
import com.connectify.services.UserService;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Server extends Application {


    private static Registry registry;
    private static Map<String, ConnectedUser> connectedUsers;

    private static ScheduledExecutorService executor;



    @Override
    public void init() throws Exception {
        super.init();
        connectedUsers = new HashMap<>();
        powerUp();
    }

    @Override
    public void start(Stage stage){
        Parent root = ViewLoader.getInstance().getMainPane();
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinHeight(750);
        stage.setMinWidth(1300);
        stage.setScene(scene);
        stage.onCloseRequestProperty().set(e -> powerDown());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    public static Map<String, ConnectedUser> getConnectedUsers(){
        return connectedUsers;
    }

    public static void powerUp(){
        try{
            registry = LocateRegistry.createRegistry(1099);
            ServerAPI server = new ServerController();
            registry.rebind("server", server);
            System.out.println("Server is running...");
        } catch (RemoteException e){
            System.err.println("Couldn't power up server: " + e.getMessage());
            System.exit(1);
        }
        Runnable pingUsers = () -> {
            UserService userService = new UserService();
            for (var user : connectedUsers.keySet()) {
                try {
                    connectedUsers.get(user).ping();
                } catch (RemoteException e) {
                    System.err.println("user " + user + " did not respond");
                    userService.logoutUser(user);
                }
            }
        };
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(pingUsers, 0, 5, SECONDS);
    }

    public static void powerDown(){
        try{
            for(var user : connectedUsers.values()){
                user.receiveNotification("Server is down", "Server is down. Contact the admin and try to reconnect later.");
                user.forceLogout();
            }
            UserService userService = new UserService();
            userService.logoutAllUser();
            connectedUsers.clear();
            executor.shutdown();
            registry.unbind("server");
            UnicastRemoteObject.unexportObject(registry, true);
            System.out.println("Server is shutdown...");
        } catch (RemoteException | NotBoundException e){
            System.err.println("Couldn't power down server: " + e.getMessage());
            System.exit(1);
        }
    }
}
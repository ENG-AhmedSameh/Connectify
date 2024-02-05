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

public class Server extends Application {


    private static Registry registry;
    private static Map<String, ConnectedUser> connectedUsers;

    private static ScheduledExecutorService statisticsScheduler;

    @Override
    public void init() throws Exception {
        super.init();
        connectedUsers = new HashMap<>();
        powerUp();
    }

    @Override
    public void start(Stage stage) throws IOException {
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
            System.out.println("Server is running...");
            registry = LocateRegistry.createRegistry(1099);
            ServerAPI server = new ServerController();
            registry.rebind("server", server);
            statisticsScheduler = Executors.newSingleThreadScheduledExecutor();
        } catch (RemoteException e){
            System.err.println("Couldn't power up server: "+e.getMessage());
            System.exit(1);
        }
    }

    public static void powerDown(){
        try{
            System.out.println("Server is shutdown...");
            for(var user : connectedUsers.values()){
                UserService userService = new UserService();
                userService.logoutUser(user.getPhoneNumber());
                user.receiveNotification("Server is down", "Server is down. Contact the admin and try to reconnect later.");
                user.forceLogout();
            }
            registry.unbind("server");
            UnicastRemoteObject.unexportObject(registry, true);
            statisticsScheduler.shutdown();
        } catch (RemoteException | NotBoundException e){
            System.err.println("Couldn't power down server: " + e.getMessage());
            System.exit(1);
        }
    }

    public static ScheduledExecutorService getStatisticsScheduler() {
        return statisticsScheduler;
    }
}
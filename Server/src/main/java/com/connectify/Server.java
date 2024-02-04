package com.connectify;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.controller.ServerController;
import com.connectify.loaders.ViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
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
            connectedUsers = new HashMap<>();
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
                user.receiveNotification("Server is down", "Server is down. Please exit the application and reconnect later.");
            }
            UnicastRemoteObject.unexportObject(registry, true);
            connectedUsers = null;
            statisticsScheduler.shutdown();
        } catch (RemoteException e){
            System.err.println("Couldn't power down server: "+e.getMessage());
            System.exit(1);
        }
    }

    public static ScheduledExecutorService getStatisticsScheduler() {
        return statisticsScheduler;
    }
}
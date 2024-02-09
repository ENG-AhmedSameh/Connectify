package com.connectify.controller.fxmlcontrollers;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.app.Server;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.rmi.RemoteException;

public class AnnouncementController{


    @FXML
    private BorderPane announcementPane;
    @FXML
    private TextField title;
    @FXML
    private TextArea body;

    @FXML
    public void onSendClick(){
        System.out.println("Sending Announcement...");
        String title = this.title.getText();
        String body = this.body.getText();
        var usersMap =  Server.getConnectedUsers();
        for(ConnectedUser  user : usersMap.values()){
            try{
                user.receiveNotification(title, body);
            } catch (RemoteException e){
                System.err.println("Error sending announcement to user: " + e.getMessage());
            }
        }
        this.title.clear();
        this.body.clear();
    }
}

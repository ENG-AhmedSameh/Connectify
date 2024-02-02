package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.ImageBioChangeRequest;
import com.connectify.utils.CurrentUser;
import com.connectify.utils.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ImageBioController {
    @FXML
    private AnchorPane signUpPane;
    @FXML
    private Circle profileImage;
    @FXML
    private TextArea bioTextArea;

    private File selectedImage;

    public void chooseProfileHandler(){
        FileChooser fileChooser = new FileChooser();
        selectedImage = fileChooser.showOpenDialog(null);
        if(selectedImage != null){
            System.out.println(selectedImage.getAbsolutePath());
            ImagePattern profile = new ImagePattern(new Image(selectedImage.toURI().toString()));
            profileImage.setFill(profile);
        }

    }
    public void finishHandler(){
        ImageBioChangeRequest request = createImageBioChangeRequest();
        try {
            ServerAPI server = (ServerAPI) Client.getRegistry().lookup("server");
            server.changeProfileAndBio(request);
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("Server not found: " + e.getMessage());
        }
        StageManager.getInstance().switchToHome();
    }


    public void skipHandler(){
        StageManager.getInstance().switchToHome();
    }

    private ImageBioChangeRequest createImageBioChangeRequest() {
        try {
            String phoneNumber = Client.getConnectedUser().getPhoneNumber();
            return new ImageBioChangeRequest(phoneNumber, selectedImage, bioTextArea.getText());
        } catch (RemoteException e) {
            System.err.println("Couldn't get user: " + e.getMessage());
        }
        return null;
    }

}

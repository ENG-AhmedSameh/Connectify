package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.ImageBioChangeRequest;
import com.connectify.utils.CurrentUser;
import com.connectify.utils.RemoteManager;
import com.connectify.utils.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.*;
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
        RemoteManager.getInstance().changeProfileAndBio(request);
        StageManager.getInstance().switchToHome();
    }


    public void skipHandler(){
        StageManager.getInstance().switchToHome();
    }

    private ImageBioChangeRequest createImageBioChangeRequest() {
        try {
            String phoneNumber = Client.getConnectedUser().getPhoneNumber();
            if(selectedImage == null){
                selectedImage = new File(getClass().getResourceAsStream("/images/profile.png").toString());
            }

            return new ImageBioChangeRequest(phoneNumber, fileToByteArray(selectedImage), bioTextArea.getText());
        } catch (RemoteException e) {
            System.err.println("Couldn't get user: " + e.getMessage());
        }
        return null;
    }
    private byte[] fileToByteArray(File file){
        try(FileInputStream fis = new FileInputStream(file)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                baos.write(buf, 0, readNum);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            System.err.println("Io Error in picture file, details: "+e.getMessage());
            return null;
        }
    }


}

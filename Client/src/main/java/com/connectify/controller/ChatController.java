package com.connectify.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private Circle pictureClip;
    @FXML
    private ImageView pictureImageView;
    @FXML
    private Circle chatImage;

    @FXML
    private Text chatName;

    @FXML
    private VBox chatWindow;

    @FXML
    private Text membersCount;

    @FXML
    private TextField sendBox;

    @FXML
    private ImageView htmlEditorImageView;
    @FXML
    private ImageView attachmentImageView;
    @FXML
    private ImageView sendImageView;
    @FXML
    private Circle statusCircle;
    private Image picture;
    private String name;
    private int chatId;

    public ChatController(int chatId,String name,Image pic) {
        picture = pic;
        this.name = name;
        this.chatId = chatId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeEventHandlers();
        pictureImageView.setImage(picture);
        chatName.setText(name);
    }

    private void initializeEventHandlers() {
        htmlEditorImageView.setOnMouseClicked((MouseEvent event) ->{
            openHTMLEditorHandler();
        });
        attachmentImageView.setOnMouseClicked((MouseEvent event) ->{
            attachmentHandler();
        });
        sendImageView.setOnMouseClicked((MouseEvent event) ->{
            sendHandler();
        });
    }

    void attachmentHandler() {
        System.out.println("Attachment pressed");
    }

    void openHTMLEditorHandler() {
        System.out.println("Edit pressed");
    }

    void sendHandler() {
        System.out.println("send pressed");
    }


}

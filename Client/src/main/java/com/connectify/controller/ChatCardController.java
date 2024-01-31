package com.connectify.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ChatCardController implements Initializable {

    @FXML
    private Label chatNameTextField;

    @FXML
    private ImageView chatPictureImageView;

    @FXML
    private Label lastMessageLabel;

    @FXML
    private Label lastMessageTimeLabel;

    @FXML
    private HBox messageHBox;

    @FXML
    private Label nChatUnreadMessagesLabel;

    int chatId,unread;
    String name, lastMessage;
    byte[] picture;
    Timestamp timestamp;
    public ChatCardController() {
    }


    public ChatCardController(int chatId, int unread, String name, byte[] picture, String lastMessage, Timestamp timestamp) {
        this.chatId = chatId;
        this.unread = unread;
        this.name = name;
        this.picture = picture;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setChatName();
        setChatPhoto();
        setUnreadMessagesNumber(unread);
        setLastMessage(lastMessage);
        setLastMessageTime(timestamp);
    }
    private void setChatPhoto(){
        chatPictureImageView.setImage(convertToJavaFXImage(picture,chatPictureImageView.getFitWidth(),chatPictureImageView.getFitHeight()));
    }
    private void setChatName(){
        chatNameTextField.setText(name);
    }
    public void setLastMessage(String message){
        lastMessageLabel.setText(message);
    }
    public void setUnreadMessagesNumber(int unreadMessages){
        if(unreadMessages==0){
            nChatUnreadMessagesLabel.setText("");
            nChatUnreadMessagesLabel.setVisible(false);
        }else{
            nChatUnreadMessagesLabel.setText(String.valueOf(unread));
            nChatUnreadMessagesLabel.setVisible(true);
        }
    }
    public void setLastMessageTime(Timestamp timestamp){
        LocalDateTime datetime = timestamp.toLocalDateTime();
        if(datetime.equals(LocalDate.now()))
            lastMessageTimeLabel.setText(datetime.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
        else
            lastMessageTimeLabel.setText(timestamp.toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }
    public Image convertToJavaFXImage(byte[] bytes, double width, double height) {
        if(bytes == null){
            String imagePath = "F:\\ITI Projects\\Connectify\\Client\\src\\main\\resources\\images\\profile.png";
            File imageFile = new File(imagePath);
            return new Image(imageFile.toURI().toString());

        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        return new Image(inputStream, width, height, false, true);
    }

    public void paneOnClicked(MouseEvent mouseEvent) {
        System.out.println("ya raaaaaaaab");
    }

//    @FXML
//    private void onMouseEnteredHandler(ActionEvent event){
//        messageHBox.setStyle("-fx-background-color: lightgreen;");
//    }


}

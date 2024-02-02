package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.ChatMemberDTO;
import com.connectify.loaders.ViewLoader;
import com.connectify.utils.ChatPaneFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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

    @FXML
    private AnchorPane chatCardPane;

    int chatId,unread;
    String name, lastMessage;
    byte[] pictureBytes;
    Image pictureImage;
    Timestamp timestamp;
    public ChatCardController() {
    }


    public ChatCardController(int chatId, int unread, String name, byte[] picture, String lastMessage, Timestamp timestamp) {
        this.chatId = chatId;
        this.unread = unread;
        this.name = name;
        this.pictureBytes = picture;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeEventHandlers();
        setChatName();
        setChatPhoto();
        setUnreadMessagesNumber(unread);
        setLastMessage(lastMessage);
        setLastMessageTime(timestamp);
    }

    private void initializeEventHandlers() {
        chatCardPane.setOnMouseClicked((MouseEvent event)->{

            displayChat();
        });
    }

    private void setChatPhoto(){
        chatPictureImageView.setImage(convertToJavaFXImage(pictureBytes,chatPictureImageView.getFitWidth(),chatPictureImageView.getFitHeight()));
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
            String imagePath = "target/classes/images/profile.png";
            File imageFile = new File(imagePath);
            pictureImage = new Image(imageFile.toURI().toString());
            return pictureImage;
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        pictureImage = new Image(inputStream, width, height, false, true);
        return pictureImage;
    }

    private void displayChat(){
        BorderPane chatPane = ChatPaneFactory.getChatPane(chatId,name,pictureImage);
        ViewLoader loader = ViewLoader.getInstance();
        loader.switchToChat(chatPane,chatCardPane.getScene());
    }
    private void prepareChatDB(){
        ServerAPI server = null;
        try {
            server = (ServerAPI) Client.getRegistry().lookup("server");
            ChatMemberDTO chatMemberDTO = new ChatMemberDTO();
            chatMemberDTO.setChatId(chatId);
            chatMemberDTO.setMember(Client.getConnectedUser().getPhoneNumber());
            server.prepareCurrentChat(chatMemberDTO);
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Couldn't find server, details:  "+e.getMessage());
        }
    }
}

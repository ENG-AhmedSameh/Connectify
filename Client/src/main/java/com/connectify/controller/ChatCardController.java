package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.ChatMemberDTO;
import com.connectify.loaders.ViewLoader;
import com.connectify.utils.ChatManagerFactory;
import com.connectify.utils.ChatPaneFactory;
import com.connectify.utils.CurrentUser;
import com.connectify.utils.RemoteManager;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
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

    private int chatId;
    private IntegerProperty unread = new SimpleIntegerProperty();
    private StringProperty chatName = new SimpleStringProperty();
    private StringProperty lastMessage= new SimpleStringProperty();
    private byte[] pictureBytes;
    private Image pictureImage;
    private Timestamp timestamp;
    private ObjectProperty<LocalDateTime> timestampProperty;
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private StringBinding formattedTimestamp;
    public ChatCardController() {
    }


    public ChatCardController(int chatId, int unread, String name, byte[] picture, String lastMessage, Timestamp timestamp) {
        this.chatId = chatId;
        this.unread.setValue(unread);
        this.chatName.setValue(name);
        this.pictureBytes = picture;
        this.lastMessage.setValue(lastMessage);
        this.timestamp = timestamp;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeEventHandlers();
        if(unread.getValue()==0)
            nChatUnreadMessagesLabel.setVisible(false);
        chatNameTextField.textProperty().bind(chatName);
        setChatPhoto();
        nChatUnreadMessagesLabel.textProperty().bind(unread.asString());
        lastMessageLabel.textProperty().bind(lastMessage);
        timestampProperty = new SimpleObjectProperty<>(timestamp.toLocalDateTime());
        bindLastMessageTimeStamp();
    }
    private void bindLastMessageTimeStamp(){
        formattedTimestamp = new StringBinding() {
            {
                super.bind(timestampProperty);
            }
            @Override
            protected String computeValue() {
                LocalDateTime timestamp = timestampProperty.get();
                LocalDate today = LocalDate.now();
                if (timestamp.toLocalDate().equals(today)) {
                    return timeFormatter.format(timestamp);
                } else {
                    return dateFormatter.format(timestamp);
                }
            }
        };
        lastMessageTimeLabel.textProperty().bind(formattedTimestamp);
    }

    private void initializeEventHandlers() {
        chatCardPane.setOnMouseClicked((MouseEvent event)->{
            displayChat();
            prepareChatDB();
        });
    }

    private void setChatPhoto(){
        chatPictureImageView.setImage(convertToJavaFXImage(pictureBytes,chatPictureImageView.getFitWidth(),chatPictureImageView.getFitHeight()));
        //chatPictureImageView.imageProperty().bind(pictureBytes);
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
        CurrentUser.getChatManagerFactory().setActiveChatID(chatId);
        setUnreadMessagesNumber(0);
        BorderPane chatPane = CurrentUser.getChatPaneFactory().getChatPane(chatId, chatName.get(),pictureImage);
        ViewLoader loader = ViewLoader.getInstance();
        loader.switchToChat(chatPane,chatCardPane.getScene());
    }
    private void prepareChatDB(){
        try {
            ChatMemberDTO chatMemberDTO = new ChatMemberDTO();
            chatMemberDTO.setChatId(chatId);
            chatMemberDTO.setMember(Client.getConnectedUser().getPhoneNumber());
            RemoteManager.getInstance().prepareCurrentChat(chatMemberDTO);
        } catch (RemoteException e) {
            System.err.println("Couldn't find server, details:  "+e.getMessage());
        }
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getUnread() {
        return unread.get();
    }

    public void setUnreadMessagesNumber(int unread) {
        this.unread.set(unread);
        nChatUnreadMessagesLabel.setVisible(unread != 0);
    }
    public void updateUnreadMessagesNumber(){
        if(CurrentUser.getChatManagerFactory().getActiveChatID()!=chatId)
            this.unread.setValue(unread.getValue()+1);
    }
    public int getUnreadMessagesNumber() {
        return this.unread.get();
    }

    public String getChatName() {
        return chatName.get();
    }
    public void setChatName(String  chatName) {
        this.chatName.setValue(chatName);
    }


    public String getLastMessage() {
        return lastMessage.get();
    }
    public void setLastMessage(String message) {
        lastMessage.setValue(message);
    }

    public byte[] getPictureBytes() {
        return pictureBytes;
    }

    public void setPictureBytes(byte[] pictureBytes) {
        this.pictureBytes = pictureBytes;
    }


    public Timestamp getLastMessageTimestamp() {
        return timestamp;
    }
    public ObjectProperty<LocalDateTime> getLastMessageTimeProperty() {
        return timestampProperty;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        timestampProperty.setValue(timestamp.toLocalDateTime());
    }

    public void updateCardPosition(AnchorPane chatCardPane) {
        CurrentUser.getAllChatsController().rearrangeChatCardController(chatCardPane);
    }
}

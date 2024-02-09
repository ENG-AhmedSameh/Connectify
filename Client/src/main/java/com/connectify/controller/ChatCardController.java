package com.connectify.controller;

import com.connectify.dto.ChatMemberDTO;
import com.connectify.loaders.ViewLoader;
import com.connectify.utils.CurrentUser;
import com.connectify.utils.ImageConverter;
import com.connectify.utils.RemoteManager;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

import java.net.URL;
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
    private Circle chatPicture;

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

    @FXML
    private Circle statusCircle;

    private int chatId;
    private IntegerProperty unread = new SimpleIntegerProperty();
    private StringProperty chatName = new SimpleStringProperty();
    private StringProperty lastMessage= new SimpleStringProperty();
    private byte[] pictureBytes;
    private byte[] pictureImage;
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
        if(!CurrentUser.getChatManagerFactory().getChatManager(chatId).isPrivateChat())
            statusCircle.setVisible(false);
        else
            statusCircle.fillProperty().bind(CurrentUser.getChatManagerFactory().getChatManager(chatId).getcolorProperty());
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
        chatPicture.setFill(ImageConverter.convertBytesToImagePattern(pictureBytes));
    }

    private void displayChat(){
        CurrentUser.getChatManagerFactory().setActiveChatID(chatId);
        setUnreadMessagesNumber(0);
        BorderPane chatPane = CurrentUser.getChatPaneFactory().getChatPane(chatId, chatName.get(),pictureBytes);
        ViewLoader loader = ViewLoader.getInstance();
        loader.switchToChat(chatPane,chatCardPane.getScene());
    }
    private void prepareChatDB(){
        try {
            ChatMemberDTO chatMemberDTO = new ChatMemberDTO();
            chatMemberDTO.setChatId(chatId);
            chatMemberDTO.setMember(CurrentUser.getInstance().getPhoneNumber());
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
        {
            nChatUnreadMessagesLabel.setVisible(true);
            this.unread.setValue(unread.getValue()+1);
        }
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

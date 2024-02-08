package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.MessageDTO;
import com.connectify.dto.MessageSentDTO;
import com.connectify.loaders.ChatCardLoader;
import com.connectify.mapper.MessageMapper;
import com.connectify.model.entities.Message;
import com.connectify.model.entities.User;
import com.connectify.model.enums.Mode;
import com.connectify.utils.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ChatController implements Initializable {

    @FXML
    private BorderPane chatsBorderPane;

    @FXML
    private ImageView attachmentImageView;

    @FXML
    private Label chatName;

    @FXML
    private ImageView htmlEditorImageView;

    @FXML
    private Text membersCount;

    @FXML
    private ListView<Message> messagesList;

    @FXML
    private Circle pictureClip;

    @FXML
    private Circle chatPicture;

    @FXML
    private TextArea sendBox;

    @FXML
    private ImageView sendImageView;

    @FXML
    private Circle statusCircle;

    private ObservableList<Message> messages;

    private final int chatID;

    private final String name;

    private final byte[] image;

    public SortedList<Message> sortedMessagesList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image chatBackgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/chat-backGround.jpg")));
        //chatBackgroundImage.
        BackgroundImage backgroundImage = new BackgroundImage(
                chatBackgroundImage,
                BackgroundRepeat.REPEAT, // How the image should be repeated along the X axis
                BackgroundRepeat.NO_REPEAT, // How the image should be repeated along the Y axis
                BackgroundPosition.DEFAULT, // Position of the image
                BackgroundSize.DEFAULT);   // Size of the image
        chatsBorderPane.setBackground(new Background(backgroundImage));

        sendBox.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER && !keyEvent.isShiftDown()) {
                sendHandler();
                keyEvent.consume();
            }else if(keyEvent.getCode() == KeyCode.ENTER && keyEvent.isShiftDown()){
                sendBox.appendText("\n");
            }
        });

        if(!CurrentUser.getChatManagerFactory().getChatManager(chatID).isPrivateChat())
            statusCircle.setVisible(false);
        else
            statusCircle.fillProperty().bind(CurrentUser.getChatManagerFactory().getChatManager(chatID).getcolorProperty());
        chatName.setText(name);
        chatPicture.setFill(ImageConverter.convertBytesToImagePattern(image));
        initializeListView();
    }

    private void initializeListView() {
        messages = CurrentUser.getMessageList(chatID);
        sortedMessagesList = new SortedList<>(messages, Comparator.comparing(Message::getTimestamp));
        messagesList.setItems(sortedMessagesList);
        setListViewCellFactory();
        loadHistoryMessages();
        messagesList.scrollTo(messagesList.getItems().size()*2);
        messages.addListener((ListChangeListener<Message>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    messagesList.scrollTo(messagesList.getItems().size()*2);
                }
            }
        });
    }

    private void loadHistoryMessages() {
        Integer firstReceivedId = CurrentUser.getChatFirstReceivedMessageId(chatID);
        List<MessageDTO> historyMeessageDtoList = RemoteManager.getInstance().getAllChatMessages(chatID,firstReceivedId);
        messages.addAll(MessageMapper.INSTANCE.messageDtoListToMessageList(historyMeessageDtoList));
    }


    public ChatController(int chatID, String name, byte[] image){
        this.chatID = chatID;
        this.name = name;
        this.image = image;
    }

    public void sendHandler(){
        if(!Objects.equals(sendBox.getText(), "")){
            try {
                MessageSentDTO messageSentDTO = new MessageSentDTO(CurrentUser.getInstance().getPhoneNumber(),chatID,sendBox.getText(),new Timestamp(System.currentTimeMillis()), null);
                appendMessage(messageSentDTO);
            } catch (RemoteException e) {
                System.err.println("Can't find server, details: "+e.getMessage());
            }
        }
    }
    public void sendChatBotMessage(String content){
        try {
            MessageSentDTO messageSentDTO = new MessageSentDTO(CurrentUser.getInstance().getPhoneNumber(),chatID,content,new Timestamp(System.currentTimeMillis()), null);
            appendMessage(messageSentDTO);
        } catch (RemoteException e) {
            System.err.println("Can't find server, details: "+e.getMessage());
        }
    }

    public void attachmentHandler(){
        Stage stage = (Stage) sendBox.getScene().getWindow();
        FileChooser  fileChooser = new FileChooser();
        fileChooser.setTitle("Select file to send");
        File file = fileChooser.showOpenDialog(stage);
        Runnable sendAttachmentTask = () -> {
            if(file != null){
                try{
                    MessageSentDTO messageSentDTO = new MessageSentDTO(CurrentUser.getInstance().getPhoneNumber(), chatID, file.getName(), new Timestamp(System.currentTimeMillis()), file);
                    appendMessage(messageSentDTO);
                    RemoteManager.getInstance().sendAttachment(messageSentDTO);
                } catch (RemoteException e){
                    System.err.println("Remote Exception: " + e.getMessage());
                }
            }
        };
        new Thread(sendAttachmentTask).start();
    }

    public void htmlEditorHandler(){

    }

    private void appendMessage(MessageSentDTO messageSentDTO){
        MessageMapper mapper = MessageMapper.INSTANCE;
        Message message =mapper.messageSentDtoTOMessage(messageSentDTO);
        ChatCardHandler.updateChatCard(message);
        RemoteManager.getInstance().sendMessage(messageSentDTO);
        //TODO render send message
        messages.add(message);
        sendBox.clear();
    }

    private void setListViewCellFactory(){
        messagesList.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            public ListCell<Message> call(ListView<Message> param) {
                return new ListCell<>(){
                    @Override
                    public void updateItem(Message message, boolean empty) {
                        super.updateItem(message, empty);
                        if (!empty) {
                            HBox root = getMessageHBox(message);
                            Platform.runLater(()->{
                                setGraphic(root);
                            });
                        } else {
                            Platform.runLater(()->{
                                setText(null);
                                setGraphic(null);
                            });
                        }
                    }
                };
            }
        });
    }

    private HBox getMessageHBox(Message message){
        FXMLLoader loader;
        HBox root;
        if (message != null) {
            try {
                if(Objects.equals(message.getSender(), CurrentUser.getInstance().getPhoneNumber())){
                    if(message.getAttachmentId() != null){
                        loader= new FXMLLoader(getClass().getResource("/views/SentAttachmentHBox.fxml"));
                        loader.setController(new MessageHBoxController(message.getContent(),message.getTimestamp()));
                        root = loader.load();
                        root.addEventHandler(MouseEvent.MOUSE_CLICKED, createEventHandler(message.getAttachmentId()));
                    }
                    else {
                        loader= new FXMLLoader(getClass().getResource("/views/SentMessageHBox.fxml"));
                        loader.setController(new MessageHBoxController(message.getContent(),message.getTimestamp()));
                        root = loader.load();
                    }
                }
                else{
                    if(CurrentUser.getChatManagerFactory().getChatManager(chatID).isPrivateChat()){
                        if(message.getAttachmentId() != null){
                            loader= new FXMLLoader(getClass().getResource("/views/ReceivedAttachmentHBox.fxml"));
                            loader.setController(new MessageHBoxController(message.getContent(),message.getTimestamp()));
                            root = loader.load();
                            // TODO fix this line
                            //ImageView icon = (ImageView) root.lookup("downloadIcon");
                            root.addEventHandler(MouseEvent.MOUSE_CLICKED, createEventHandler(message.getAttachmentId()));
                        }
                        else {
                            loader = new FXMLLoader(getClass().getResource("/views/ReceivedMessageHBox.fxml"));
                            loader.setController(new MessageHBoxController(message.getContent(),message.getTimestamp()));
                            root = loader.load();
                        }
                    }
                    else{
                        if(message.getAttachmentId() != null){
                            loader= new FXMLLoader(getClass().getResource("/views/SentAttachmentHBox.fxml"));
                            loader.setController(new MessageHBoxController(message.getContent(),message.getTimestamp()));
                            root = loader.load();
                            root.addEventHandler(MouseEvent.MOUSE_CLICKED, createEventHandler(message.getAttachmentId()));
                        }
                        else {
                            loader = new FXMLLoader(getClass().getResource("/views/GroupMessageHBox.fxml"));
                            GroupMessageHBoxController controller = new GroupMessageHBoxController();
                            loader.setController(controller);
                            root = loader.load();
                            ChatManager chatManager= CurrentUser.getChatManagerFactory().getChatManager(chatID);
//                        if(Objects.equals(chatManager.getGroupLastSender(), message.getSender())){
//                            controller.setSameSenderMessageStyle(message.getContent(),message.getTimestamp());
//                            chatManager.setGroupLastSender(message.getSender());
//                        }else{
                            User user = chatManager.getUserInfo(message.getSender());
                            controller.setDifferentSenderMessageStyle(user.getName(),user.getPicture(),message.getContent(),message.getTimestamp());
                            chatManager.setGroupLastSender(message.getSender());
                            //}
                        }
                    }
                }
                return root;
            } catch (IOException e) {
                System.err.println(Arrays.toString(e.getStackTrace()));
            }
        }
        return null;
    }



    private EventHandler<MouseEvent> createEventHandler(Integer attachmentID){
        return (EventHandler<MouseEvent>) (e) -> {
            Callable<File> task = () -> RemoteManager.getInstance().getAttachment(attachmentID);
            var future = new FutureTask<File>(task);
            var thread = new Thread(future);
            thread.start();
            try {
                File attachment = future.get();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File");
                fileChooser.setInitialFileName(attachment.getName());
                File selectedFile = fileChooser.showSaveDialog(chatName.getScene().getWindow());
                if (selectedFile != null) {
                    try {
                        Files.copy(attachment.toPath(), selectedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("File saved successfully to: " + selectedFile.getAbsolutePath());
                    } catch (IOException ex) {
                        System.err.println("Failed to save file: " + attachment.getPath());
                    }
                }
            } catch (InterruptedException ex) {
                System.err.println("Interrupted Exception: " + ex.getMessage());
            } catch (ExecutionException ex) {
                System.err.println("Execution Exception: " + ex.getMessage());
            }
        };
    }

}


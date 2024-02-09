package com.connectify.controller;

import com.connectify.dto.MessageDTO;
import com.connectify.dto.MessageSentDTO;
import com.connectify.model.entities.Message;
import com.connectify.model.entities.User;
import com.connectify.mapper.*;
import com.connectify.utils.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private final ExecutorService executor;


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
        messagesList.setItems(messages);
        Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));
        sortedMessagesList = new SortedList<>(messages, Comparator.comparing(Message::getTimestamp));
        messagesList.setItems(sortedMessagesList);
        setListViewCellFactory();
        loadHistoryMessages();
        //TODO scroll down automatically
        messagesList.scrollTo(messagesList.getItems().size()*2);
//        messages.addListener((ListChangeListener<Message>) change -> {
//            while (change.next()) {
//                if (change.wasAdded()) {
//                    messagesList.scrollTo(messagesList.getItems().size()*2);
//                }
//            }
//        });
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
        executor = Executors.newCachedThreadPool();
    }

    public void sendHandler(){
        if(!Objects.equals(sendBox.getText(), "")){
            try {
                MessageSentDTO messageSentDTO = new MessageSentDTO(CurrentUser.getInstance().getPhoneNumber(),chatID,sendBox.getText(),new Timestamp(System.currentTimeMillis()), null);
                RemoteManager.getInstance().sendMessage(messageSentDTO);
                appendMessage(messageSentDTO);
            } catch (RemoteException e) {
                System.err.println("Can't find server, details: "+e.getMessage());
            }
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
                    byte[] bytes = Files.readAllBytes(file.toPath());
                    MessageSentDTO messageSentDTO = new MessageSentDTO(CurrentUser.getInstance().getPhoneNumber(), chatID, file.getName(), new Timestamp(System.currentTimeMillis()), bytes);
                    RemoteManager.getInstance().sendAttachment(messageSentDTO);
                    appendMessage(messageSentDTO);
                    System.out.println("attachment sent");
                } catch (RemoteException e){
                    System.err.println("Remote Exception: " + e.getMessage());
                } catch (IOException e) {
                    System.err.println("IO Exception: " + e.getMessage());
                }
            }
        };
        executor.execute(sendAttachmentTask);
    }

    public void htmlEditorHandler(){

    }

    private void appendMessage(MessageSentDTO messageSentDTO){
        MessageMapper mapper = MessageMapper.INSTANCE;
        Message message = mapper.messageSentDtoTOMessage(messageSentDTO);
        ChatCardHandler.updateChatCard(message);
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
            Callable<byte[]> task = () -> RemoteManager.getInstance().getAttachment(attachmentID);
            try {
                byte[] attachment = executor.submit(task).get();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File");
                File selectedFile = fileChooser.showSaveDialog(chatName.getScene().getWindow());
                if (selectedFile != null) {
                    try {
                        Files.write(selectedFile.toPath(), attachment);
                        System.out.println("File saved successfully to: " + selectedFile.getAbsolutePath());
                    } catch (IOException ex) {
                        System.err.println("Failed to save file: " + selectedFile.toPath());
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


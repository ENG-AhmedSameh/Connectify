package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.MessageSentDTO;
import com.connectify.mapper.MessageMapper;
import com.connectify.model.entities.Message;
import com.connectify.model.entities.User;
import com.connectify.utils.ChatCardHandler;
import com.connectify.utils.ChatManager;
import com.connectify.utils.ChatManagerFactory;
import com.connectify.utils.CurrentUser;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private ImageView attachmentImageView;

    @FXML
    private Text chatName;

    @FXML
    private ImageView htmlEditorImageView;

    @FXML
    private Text membersCount;

    @FXML
    private ListView<Message> messagesList;

    @FXML
    private Circle pictureClip;

    @FXML
    private ImageView pictureImageView;

    @FXML
    private TextField sendBox;

    @FXML
    private ImageView sendImageView;

    @FXML
    private Circle statusCircle;

    private ObservableList<Message> messages;

    private final int chatID;

    private final String name;

    private final Image image;

    ServerAPI server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatName.setText(name);
        pictureImageView.setImage(image);
        setListViewCellFactory();
        messages = CurrentUser.getMessageList(chatID);
        messagesList.setItems(messages);
    }


    public ChatController(int chatID, String name, Image image){
        this.chatID = chatID;
        this.name = name;
        this.image = image;
        try {
            server = (ServerAPI) Client.getRegistry().lookup("server");
        } catch (RemoteException | NotBoundException e) {

        }
    }

    public void sendHandler(){
        if(!Objects.equals(sendBox.getText(), "")){
            try {
                MessageSentDTO messageSentDTO = new MessageSentDTO(Client.getConnectedUser().getPhoneNumber(),chatID,sendBox.getText(),new Timestamp(System.currentTimeMillis()));
                MessageMapper mapper = MessageMapper.INSTANCE;
                Message message =mapper.messageSentDtoTOMessage(messageSentDTO);
                ChatCardHandler.updateChatCard(message);
                server.sendMessage(messageSentDTO);
                //TODO render send message
                messages.add(message);
                sendBox.clear();
            } catch (RemoteException e) {
                System.err.println("Can't find server, details: "+e.getMessage());
            }
        }
    }


    public void attachmentHandler(){

    }

    public void htmlEditorHandler(){

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
                if(Objects.equals(message.getSender(), Client.getConnectedUser().getPhoneNumber())){
                    loader= new FXMLLoader(getClass().getResource("/views/SentMessageHBox.fxml"));
                    loader.setController(new MessageHBoxController(message.getContent(),message.getTimestamp()));
                    root = loader.load();
                }
                else{
                    if(ChatManagerFactory.getChatManager(chatID).isPrivateChat()){
                        loader = new FXMLLoader(getClass().getResource("/views/ReceivedMessageHBox.fxml"));
                        loader.setController(new MessageHBoxController(message.getContent(),message.getTimestamp()));
                        root = loader.load();
                    }else{
                        loader = new FXMLLoader(getClass().getResource("/views/GroupMessageHBox.fxml"));
                        GroupMessageHBoxController controller = new GroupMessageHBoxController();
                        loader.setController(controller);
                        root = loader.load();
                        ChatManager chatManager= ChatManagerFactory.getChatManager(chatID);
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
                return root;
            } catch (IOException e) {
                System.err.println(Arrays.toString(e.getStackTrace()));
            }
        }
        return null;
    }

}

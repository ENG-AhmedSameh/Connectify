package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.MessageSentDTO;
import com.connectify.utils.CurrentUser;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
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
    private ListView<String> messagesList;

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

    private ObservableList<String> messages;

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
        //System.out.println("sh8aaaalllllll");
        try {
            MessageSentDTO messageSentDTO = new MessageSentDTO(Client.getConnectedUser().getPhoneNumber(),chatID,sendBox.getText(),new Timestamp(System.currentTimeMillis()));
            server.sendMessage(messageSentDTO);
            //TODO render send message
            messages.add(sendBox.getText());
            sendBox.clear();
        } catch (RemoteException e) {
            System.err.println("Can't find server, details: "+e.getMessage());
        }
    }


    public void attachmentHandler(){

    }

    public void htmlEditorHandler(){

    }

    private void setListViewCellFactory() {
        messagesList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            setGraphic(new Label(item));
                        }
                    }
                };
            }
        });
    }

}

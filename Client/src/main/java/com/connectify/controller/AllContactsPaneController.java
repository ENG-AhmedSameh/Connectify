package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.ContactsDTO;
import com.connectify.loaders.ChatCardLoader;
import com.connectify.loaders.ContactCardLoader;
import com.connectify.mapper.ContactMapper;
import com.connectify.model.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class AllContactsPaneController implements Initializable {

    @FXML
    private TextField ContactSearchTextField;

    @FXML
    private AnchorPane allContactsAnchorPane;

    @FXML
    private ScrollPane allContactsScrollPane;

    private ServerAPI serverAPI;

    @FXML
    private VBox allContactsVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            serverAPI = (ServerAPI) Client.getRegistry().lookup("server");
            List<ContactsDTO> ContactDTOList = serverAPI.getContacts(Client.getConnectedUser().getPhoneNumber());
            ContactMapper mapper=ContactMapper.INSTANCE;
            List<User> contactsList = mapper.contactDTOListToUserList(ContactDTOList);
            for (User user : contactsList)
            {
                allContactsVBox.getChildren().add(ContactCardLoader.loadContactCardAnchorPane(user));
            }
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addContactOnContactsPane(){

    }

}

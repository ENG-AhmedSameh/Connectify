package com.connectify.controller;

import com.connectify.Client;
import com.connectify.dto.ContactsDTO;
import com.connectify.loaders.ChooseContactCardLoader;
import com.connectify.utils.RemoteManager;
import com.connectify.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseContactsGroupController implements Initializable {

    @FXML
    private TextField ContactSearchTextField;

    @FXML
    private Button NextButton;

    @FXML
    private ScrollPane allContactsScrollPane;

    @FXML
    private VBox allContactsVBox;

    @FXML
    private Button cancelButton;

    @FXML
    private AnchorPane chooseContactsGroupAnchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<ContactsDTO> ContactDTOList = null;
        try {
            ContactDTOList = RemoteManager.getInstance().getContacts(Client.getConnectedUser().getPhoneNumber());
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
        }
        for (ContactsDTO contact : ContactDTOList)
        {
            allContactsVBox.getChildren().add(ChooseContactCardLoader.loadChooseContactCardAnchorPane(contact));
        }
    }

    @FXML
    void NextHandler(ActionEvent event) {
        StageManager.getInstance().switchToGroupInfo();
    }

    @FXML
    void cancelHandler(ActionEvent event) {
        StageManager.getInstance().switchToChats();
    }
}

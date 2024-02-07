package com.connectify.controller;

import com.connectify.Client;
import com.connectify.dto.ContactsDTO;
import com.connectify.dto.UserProfileResponse;
import com.connectify.loaders.ChooseContactCardLoader;
import com.connectify.mapper.ContactMapper;
import com.connectify.utils.CurrentUser;
import com.connectify.utils.RemoteManager;
import com.connectify.utils.StageManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
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

    @FXML
    private Label errorLabel;

    private static List<ContactsDTO> selectedContacts = new ArrayList<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<ContactsDTO> ContactDTOList = null;
        try {
            ContactDTOList = RemoteManager.getInstance().getContacts(CurrentUser.getInstance().getPhoneNumber());
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
        try {
            selectedContacts = processSelectedContacts();

            UserProfileResponse currentUserProfile = RemoteManager.getInstance()
                    .getUserProfile(CurrentUser.getInstance().getPhoneNumber());

            ContactsDTO currentUser = ContactMapper.INSTANCE.userProfileResponseToContactsDto(currentUserProfile);
            selectedContacts.add(currentUser);

        } catch (RemoteException e) {
            System.err.println("Error when trying to get current user profile. Case: " + e.getMessage());
        }

        if (selectedContacts.size() < 2) {
            errorLabel.setText("Select at least one contact.");
            return;
        }

        StageManager.getInstance().switchToGroupInfo();
    }

    private List<ContactsDTO> processSelectedContacts() {
        List<ContactsDTO> result = new ArrayList<>();
        ObservableList<Node> list = allContactsVBox.getChildren();
        for(Node node : list) {
            ChooseContactCardController chooseContactCardcontroller = (ChooseContactCardController) node.getUserData();
            if(chooseContactCardcontroller.isSelected()) {
                result.add(chooseContactCardcontroller.getContactDTO());
            }
        }
        return result;
    }

    @FXML
    void cancelHandler(ActionEvent event) {
        StageManager.getInstance().switchToChats();
    }

    public static List<ContactsDTO> getSelectedContacts() {
        return selectedContacts;
    }
}

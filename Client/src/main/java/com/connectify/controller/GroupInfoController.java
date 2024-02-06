package com.connectify.controller;

import com.connectify.Client;
import com.connectify.dto.ChatCardsInfoDTO;
import com.connectify.dto.ContactsDTO;
import com.connectify.loaders.ChatCardLoader;
import com.connectify.utils.CurrentUser;
import com.connectify.utils.RemoteManager;
import com.connectify.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class GroupInfoController implements Initializable {

    @FXML
    private Button BackButton;

    @FXML
    private TextField groupDescriptionTextField;

    @FXML
    private AnchorPane groupInfoAnchorPane;

    @FXML
    private Button chooseGroupImageButton;

    @FXML
    private Button createButton;

    @FXML
    private TextField groupNameTextField;

    @FXML
    private Circle userImageCircle;

    private byte[] newPicture;
    private String txtFieldsOriginalStyle;

    @FXML
    void BackHandler(ActionEvent event) {
        StageManager.getInstance().switchToChooseContactsGroupPane();
    }

    @FXML
    void chooseGroupImageHandler(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                Image userImage = new Image(file.toURI().toURL().toString());
                userImageCircle.setFill(new ImagePattern(userImage));

                newPicture = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void createHandler(ActionEvent event) {
        List<ContactsDTO> list = ChooseContactsGroupController.getSelectedContacts();
        if (groupNameTextField.getText().isEmpty()) {
            groupNameTextField.setStyle("-fx-border-color: red;");
            groupNameTextField.setTooltip(hintText("Doesn't match the password in the first field"));
            return;
        }
        boolean isSuccessful = RemoteManager.getInstance().createGroup(list,
                groupNameTextField.getText(),
                groupDescriptionTextField.getText(),
                newPicture);

//        if (isSuccessful) {
//            ChatCardsInfoDTO chat = null;
//            try {
//                chat = RemoteManager.getInstance().getUserLastChatCardInfo(Client.getConnectedUser().getPhoneNumber());
//            } catch (RemoteException e) {
//                throw new RuntimeException(e);
//            }
//            AnchorPane chatCard = ChatCardLoader.loadChatCardAnchorPane(chat.getChatID(),chat.getUnreadMessagesNumber(),chat.getName(),chat.getPicture(),chat.getLastMessage(),chat.getTimestamp());
//            CurrentUser.getAllChatsController().getChatsPanesList().add(chatCard);
//        }
        StageManager.getInstance().switchToChats();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtFieldsOriginalStyle = groupNameTextField.getStyle();
    }

    private Tooltip hintText(String text) {
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: rgba(241,241,241,1); -fx-text-fill: black; -fx-background-radius: 4; -fx-border-radius: 4; -fx-opacity: 1.0;");
        tooltip.setAutoHide(false);
        tooltip.setMaxWidth(300);
        tooltip.setWrapText(true);
        tooltip.setText(text);
        return tooltip;
    }
}

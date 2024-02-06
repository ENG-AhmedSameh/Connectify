package com.connectify.controller;

import com.connectify.loaders.ViewLoader;
import com.connectify.model.entities.User;
import com.connectify.utils.ChatManager;
import com.connectify.utils.CurrentUser;
import com.connectify.utils.ImageConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactCardController implements Initializable {
    User user;
    public ContactCardController(User user)
    {
        this.user=user;
    }

    @FXML
    private Label contactNameLabel;

    @FXML
    private Circle contactPicture;

    @FXML
    private Label contactPhoneNumberLabel;
    @FXML
    private Circle statusCircle;
    private int chatId;
    ChatManager chatManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactNameLabel.setText(user.getName());
        contactPicture.setFill(ImageConverter.convertBytesToImagePattern(user.getPicture()));
        contactPhoneNumberLabel.setText(user.getPhoneNumber());
        chatManager = CurrentUser.getChatManagerFactory().getContactChatManager(user.getPhoneNumber());
        chatId = chatManager.getChatID();
        if(!CurrentUser.getChatManagerFactory().getChatManager(chatId).isPrivateChat())
            statusCircle.setVisible(false);
        else
            statusCircle.fillProperty().bind(CurrentUser.getChatManagerFactory().getChatManager(chatId).getcolorProperty());
    }

    public void paneOnClicked(MouseEvent mouseEvent) {
        CurrentUser.getChatManagerFactory().setActiveChatID(chatId);
        ChatCardController cardController = chatManager.getChatCardController();
        cardController.setUnreadMessagesNumber(0);
        BorderPane chatPane = CurrentUser.getChatPaneFactory().getChatPane(chatId, cardController.getChatName(),cardController.getPictureBytes());
        ViewLoader loader = ViewLoader.getInstance();
        loader.switchToChat(chatPane,contactNameLabel.getScene());
    }

}

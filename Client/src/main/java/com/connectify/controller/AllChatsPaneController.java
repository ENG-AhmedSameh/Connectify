package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.ChatCardsInfoDTO;
import com.connectify.dto.ChatMemberDTO;
import com.connectify.loaders.ChatCardLoader;
import com.connectify.loaders.ViewLoader;
import com.connectify.mapper.ChatMemberMapper;
import com.connectify.model.entities.ChatMember;
import com.connectify.utils.ChatManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AllChatsPaneController implements Initializable {

    ViewLoader loader = ViewLoader.getInstance();

    @FXML
    private TextField chatSearchTextField;

    @FXML
    private AnchorPane allChatsAnchorPane;

    @FXML
    private ScrollPane allChatsScrollPane;

    @FXML
    private VBox allChatsVBox;

    @FXML
    private ListView<AnchorPane> allChatsListView;
    private static ObservableList<AnchorPane> chatsPanesList = FXCollections.observableArrayList();

    private ServerAPI server;
    private static String currentUserId;
    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentId) {
        currentUserId = currentId;
    }

    public AllChatsPaneController(){
    }

    public SortedList<AnchorPane> sortedAnchorPanes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            server = (ServerAPI) Client.getRegistry().lookup("server");
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("NotBoundException: " + e.getMessage());
        }
        initializeListView();
        loadAllUserChats();
        System.out.println("done");
    }

    private void initializeListView(){
        sortedAnchorPanes = new SortedList<>(chatsPanesList, (pane1, pane2) -> {
            ChatCardController controller1 = ChatManagerFactory.getChatManager(ChatCardLoader.getChatsCardId(pane1)).getChatCardController();
            ChatCardController controller2 = ChatManagerFactory.getChatManager(ChatCardLoader.getChatsCardId(pane2)).getChatCardController();
            return Objects.requireNonNull(controller2).getLastMessageTimestamp().compareTo(Objects.requireNonNull(controller1).getLastMessageTimestamp());
        });
        allChatsListView.setItems(sortedAnchorPanes);
        //allChatsListView.setItems(chatsPanesList);
        setListViewCellFactory();
    }

    private void setListViewCellFactory() {
        allChatsListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<AnchorPane> call(ListView<AnchorPane> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(AnchorPane item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            setGraphic(item);
                        }
                    }
                };
            }
        });
    }

    public void addChatOnChatPane(int chatId, int unread, String name, byte[] picture, String lastMessage, Timestamp timestamp){
        AnchorPane chatCard = ChatCardLoader.loadChatCardAnchorPane(chatId, unread,name,picture,lastMessage,timestamp);
        chatsPanesList.add(chatCard);
    }
    private void loadAllUserChats(){
        try {
            //allChatsVBox.getChildren().removeAll();
            List<ChatCardsInfoDTO> chatCardsInfoDTOS = server.getUserChatsCardsInfo(Client.getConnectedUser().getPhoneNumber());
            for(ChatCardsInfoDTO chat:chatCardsInfoDTOS)
                addChatOnChatPane(chat.getChatID(),chat.getUnreadMessagesNumber(),chat.getName(),chat.getPicture(),chat.getLastMessage(),chat.getTimestamp());
            chatCardsInfoDTOS.stream().forEach(System.out::println);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public static void clearChatsCardList(){
        chatsPanesList.clear();
    }

    public static ObservableList<AnchorPane> getChatsPanesList() {
        return chatsPanesList;
    }
}

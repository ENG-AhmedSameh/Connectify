package com.connectify.utils;

import com.connectify.dto.MessageSentDTO;
import com.connectify.mapper.*;
import com.connectify.model.entities.Message;
import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.rmi.RemoteException;
import java.sql.Timestamp;

public class ChatBot {
    private static BooleanProperty enabled = new SimpleBooleanProperty(false);
    public static String call(String input) {
        try {
            ChatterBotFactory factory = new ChatterBotFactory();

            ChatterBot bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");

            ChatterBotSession botSession = bot.createSession();

            String response = botSession.think(input);

            return response.isEmpty() ? "Sorry, I don't have an answer to that" : response;
        } catch (Exception e) {
            String errorMessage = "An error occurred while processing the chat bot request: " + e.getMessage();
            System.err.println(errorMessage);
            return "Sorry, I don't have an answer to that";
        }
    }

    public static void replyToMessage(Message receivedMessage) throws RemoteException {
        String replyContent = call(receivedMessage.getContent());
        MessageSentDTO messageSentDTO = new MessageSentDTO(CurrentUser.getInstance().getPhoneNumber(),receivedMessage.getChatId(),replyContent,new Timestamp(System.currentTimeMillis()), null,"");
        Message replyMessage = MessageMapper.INSTANCE.messageSentDtoTOMessage(messageSentDTO);
        CurrentUser.getMessageList(receivedMessage.getChatId()).add(replyMessage);
        ChatCardHandler.updateChatCard(replyMessage);
        RemoteManager.getInstance().sendMessage(messageSentDTO);
    }

    public static boolean isEnabled() {
        return enabled.get();
    }

    public static BooleanProperty enabledProperty() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        ChatBot.enabled.set(enabled);
    }
}

package com.connectify.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

public class ChatCardsInfoDTO implements Serializable {
    private int chatID;
    private int unreadMessagesNumber;
    private String name;
    private byte[] picture;
    private String lastMessage;
    private Timestamp timestamp;

    public ChatCardsInfoDTO(int chatID, int unreadMessagesNumber, String name, byte[] picture, String lastMessage, Timestamp timestamp) {
        this.chatID = chatID;
        this.unreadMessagesNumber = unreadMessagesNumber;
        this.name = name;
        this.picture = picture;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ChatCardsInfoDTO{" +
                "chatID=" + chatID +
                ", unreadMessagesNumber=" + unreadMessagesNumber +
                ", name='" + name + '\'' +
                ", picture=" + Arrays.toString(picture) +
                ", lastMessage='" + lastMessage + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public int getUnreadMessagesNumber() {
        return unreadMessagesNumber;
    }

    public void setUnreadMessagesNumber(int unreadMessagesNumber) {
        this.unreadMessagesNumber = unreadMessagesNumber;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}

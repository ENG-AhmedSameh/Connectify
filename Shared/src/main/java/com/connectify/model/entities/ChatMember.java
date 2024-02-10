package com.connectify.model.entities;

import java.sql.Timestamp;

public class ChatMember {
    private int chatId;
    private String member;
    private Boolean isOpen = false;
    private Timestamp lastOpenedTime;
    private int unreadMessagesNumber;

    public ChatMember() {
    }

    public ChatMember(int chatId, String member) {
        this.chatId = chatId;
        this.member = member;
    }

    public ChatMember(int chatId, String member, boolean isOpen, Timestamp lastOpenedTime, int unreadMessagesNumber) {
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public int getUnreadMessagesNumber() {
        return unreadMessagesNumber;
    }

    public void setUnreadMessagesNumber(int unreadMessagesNumber) {
        this.unreadMessagesNumber = unreadMessagesNumber;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean open) {
        isOpen = open;
    }

    public Timestamp getLastOpenedTime() {
        return lastOpenedTime;
    }

    public void setLastOpenedTime(Timestamp lastOpenedTime) {
        this.lastOpenedTime = lastOpenedTime;
    }

    @Override
    public String toString() {
        return "ChatMember{" +
                "chatId=" + chatId +
                ", member='" + member + '\'' +
                ", isOpen=" + isOpen +
                ", lastOpenedTime=" + lastOpenedTime +
                ", unreadMessagesNumber=" + unreadMessagesNumber +
                '}';
    }
}


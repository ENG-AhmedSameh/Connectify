package com.connectify.model.entities;

public class ChatMember {
    private int chatId;
    private String member;
    private Integer unreadMessagesNumber;  // nullable

    public ChatMember() {
    }

    public ChatMember(int chatId, String member, Integer unreadMessagesNumber) {
        this.chatId = chatId;
        this.member = member;
        this.unreadMessagesNumber = unreadMessagesNumber;
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

    public Integer getUnreadMessagesNumber() {
        return unreadMessagesNumber;
    }

    public void setUnreadMessagesNumber(Integer unreadMessagesNumber) {
        this.unreadMessagesNumber = unreadMessagesNumber;
    }

    @Override
    public String toString() {
        return "ChatMember{" +
                "chatId=" + chatId +
                ", member='" + member + '\'' +
                ", unreadMessagesNumber=" + unreadMessagesNumber +
                '}';
    }
}


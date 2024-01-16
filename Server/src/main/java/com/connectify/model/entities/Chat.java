package com.connectify.model.entities;
public class Chat {
    private int chatId;
    private int isPrivateChat;
    private Integer numberOfMembers;  // nullable

    // Constructors
    public Chat() {
    }

    public Chat(int chatId, int isPrivateChat, Integer numberOfMembers) {
        this.chatId = chatId;
        this.isPrivateChat = isPrivateChat;
        this.numberOfMembers = numberOfMembers;
    }

    // Getters and Setters
    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getIsPrivateChat() {
        return isPrivateChat;
    }

    public void setIsPrivateChat(int isPrivateChat) {
        this.isPrivateChat = isPrivateChat;
    }

    public Integer getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(Integer numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    // toString method for debugging or displaying information
    @Override
    public String toString() {
        return "Chat{" +
                "chatId=" + chatId +
                ", isPrivateChat=" + isPrivateChat +
                ", numberOfMembers=" + numberOfMembers +
                '}';
    }
}

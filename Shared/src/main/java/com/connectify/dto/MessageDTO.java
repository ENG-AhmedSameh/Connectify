package com.connectify.dto;

import java.io.Serializable;

public class MessageDTO implements Serializable {
    private int chatId;
    private String sender;
    private String content;

    public MessageDTO(int chatId, String sender, String content) {
        this.chatId = chatId;
        this.sender = sender;
        this.content = content;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

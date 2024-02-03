package com.connectify.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class MessageSentDTO implements Serializable {
    private String sender;
    private int chatId;
    private String content;

    public MessageSentDTO(String sender, int chatId, String content) {
        this.sender = sender;
        this.chatId = chatId;
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

package com.connectify.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class MessageDTO implements Serializable {
    private int messageId;
    private String sender;
    private int chatId;
    private Timestamp timestamp;
    private String content;
    private Integer attachmentId;  // nullable
    private String messageStyle;

    public MessageDTO(int messageId, String sender, int chatId, Timestamp timestamp, String content, Integer attachmentId,String style) {
        this.messageId = messageId;
        this.sender = sender;
        this.chatId = chatId;
        this.timestamp = timestamp;
        this.content = content;
        this.attachmentId = attachmentId;
        this.messageStyle = style;
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

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getMessageStyle() {
        return messageStyle;
    }

    public void setMessageStyle(String messageStyle) {
        this.messageStyle = messageStyle;
    }
}

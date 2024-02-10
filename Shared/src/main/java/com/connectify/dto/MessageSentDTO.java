package com.connectify.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class MessageSentDTO implements Serializable {
    private String sender;
    private int chatId;
    private String content;
    private Timestamp timestamp;
    private byte[] attachment;
    private Integer attachmentId;

    private String messageStyle;

    public MessageSentDTO(String sender, int chatId, String content, Timestamp timestamp, byte[] attachment, String style) {
        this.sender = sender;
        this.chatId = chatId;
        this.content = content;
        this.timestamp= timestamp;
        this.attachment = attachment;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment( byte[] attachment) {
        this.attachment = attachment;
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

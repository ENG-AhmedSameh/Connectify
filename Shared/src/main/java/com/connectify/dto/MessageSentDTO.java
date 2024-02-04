package com.connectify.dto;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;

public class MessageSentDTO implements Serializable {
    private String sender;
    private int chatId;
    private String content;
    private Timestamp timestamp;
    private File attachment;
    private Integer attachmentId;

    public MessageSentDTO(String sender, int chatId, String content, Timestamp timestamp, File attachment) {
        this.sender = sender;
        this.chatId = chatId;
        this.content = content;
        this.timestamp= timestamp;
        this.attachment = attachment;
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

    public File getAttachment() {
        return attachment;
    }

    public void setAttachment(File attachment) {
        this.attachment = attachment;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }
}

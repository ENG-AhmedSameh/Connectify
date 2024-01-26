package model.entities;

import java.sql.Timestamp;

public class Message {
    private int messageId;
    private String sender;
    private int chatId;
    private Timestamp timestamp;
    private String content;
    private Integer attachmentId;  // nullable

    public Message() {
    }

    public Message(int messageId, String sender, int chatId, Timestamp timestamp, String content, Integer attachmentId) {
        this.messageId = messageId;
        this.sender = sender;
        this.chatId = chatId;
        this.timestamp = timestamp;
        this.content = content;
        this.attachmentId = attachmentId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", sender='" + sender + '\'' +
                ", chatId=" + chatId +
                ", timestamp=" + timestamp +
                ", content='" + content + '\'' +
                ", attachmentId=" + attachmentId +
                '}';
    }
}


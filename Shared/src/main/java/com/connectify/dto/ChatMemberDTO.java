package com.connectify.dto;

import java.io.Serializable;

public class ChatMemberDTO implements Serializable {
    private int chatId;

    private String member;
    private Integer unreadMessagesNumber;

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public ChatMemberDTO() {
    }

    public ChatMemberDTO(int chatId,int unreadMessagesNumber) {
        this.chatId = chatId;
        this.unreadMessagesNumber=unreadMessagesNumber;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }


    public Integer getUnreadMessagesNumber() {
        return unreadMessagesNumber;
    }

    public void setUnreadMessagesNumber(Integer unreadMessagesNumber) {
        this.unreadMessagesNumber = unreadMessagesNumber;
    }
}

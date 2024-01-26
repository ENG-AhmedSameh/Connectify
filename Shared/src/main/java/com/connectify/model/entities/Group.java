package com.connectify.model.entities;

import java.util.Arrays;

public class Group {
    private int groupId;
    private int chatId;
    private String name;
    private byte[] picture;
    private String description;

    public Group() {
    }

    public Group(int groupId, int chatId, String name, byte[] picture, String description) {
        this.groupId = groupId;
        this.chatId = chatId;
        this.name = name;
        this.picture = picture;
        this.description = description;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // toString method for debugging or displaying information
    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", picture=" + Arrays.toString(picture) +
                ", description='" + description + '\'' +
                '}';
    }
}
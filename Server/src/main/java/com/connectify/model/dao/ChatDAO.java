package com.connectify.model.dao;


import com.connectify.model.entities.Chat;

public interface ChatDAO extends DAO<Chat,Integer> {
    boolean isPrivateChat(int chatID);
}

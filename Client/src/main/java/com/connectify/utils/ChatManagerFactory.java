package com.connectify.utils;

import java.util.HashMap;
import java.util.Map;

public class ChatManagerFactory {
    private int activeChatID = 0;
    private final Map<Integer,ChatManager> chatManagersMap = new HashMap<>();


    public ChatManager getChatManager(int chatID){
        chatManagersMap.putIfAbsent(chatID,new ChatManager(chatID));
        return chatManagersMap.get(chatID);
    }

    public int getActiveChatID() {
        return activeChatID;
    }

    public void setActiveChatID(int activeChatID) {
        this.activeChatID = activeChatID;
    }

    public void clearChatManagersMap(){
        chatManagersMap.clear();
    }
}

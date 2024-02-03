package com.connectify.utils;

import java.util.HashMap;
import java.util.Map;

public class ChatManagerFactory {
    private static final Map<Integer,ChatManager> chatManagersMap = new HashMap<>();

    public static ChatManager getChatManager(int chatID){
        chatManagersMap.putIfAbsent(chatID,new ChatManager(chatID));
        return chatManagersMap.get(chatID);
    }
}

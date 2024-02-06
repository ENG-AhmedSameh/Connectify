package com.connectify.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatManagerFactory {
    private int activeChatID = 0;
    private final Map<Integer,ChatManager> chatManagersMap = new HashMap<>();
    private final Map<String,ChatManager> contactsChatManager = new HashMap<>();

    public ChatManager getChatManager(int chatID){
        ChatManager chatManager = new ChatManager(chatID);
        chatManagersMap.putIfAbsent(chatID,chatManager);
        String contact = chatManager.getChatContact(chatID);
        if(!Objects.equals(contact, ""))
            contactsChatManager.putIfAbsent(contact,chatManager);
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
    public ChatManager getContactChatManager(String phoneNumber){
        if(contactsChatManager.containsKey(phoneNumber))
            return contactsChatManager.get(phoneNumber);
        return null;
    }
}

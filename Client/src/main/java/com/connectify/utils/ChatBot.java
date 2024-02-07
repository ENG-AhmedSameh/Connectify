package com.connectify.utils;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ChatBot {
    private static BooleanProperty enabled = new SimpleBooleanProperty(false);
    public static String call(String input) {
        try {
            ChatterBotFactory factory = new ChatterBotFactory();

            ChatterBot bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");

            ChatterBotSession botSession = bot.createSession();

            String response = botSession.think(input);

            return response.isEmpty() ? "Sorry, I don't have an answer to that" : response;
        } catch (Exception e) {
            String errorMessage = "An error occurred while processing the chat bot request: " + e.getMessage();
            System.err.println(errorMessage);
            return "Sorry, I don't have an answer to that";
        }
    }

    public static boolean isEnabled() {
        return enabled.get();
    }

    public static BooleanProperty enabledProperty() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        ChatBot.enabled.set(enabled);
    }
}

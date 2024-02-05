package com.connectify.utils;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;

public class ChatBot {
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
}

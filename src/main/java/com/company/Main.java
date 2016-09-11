package com.company;


import me.shib.java.lib.jtelebot.models.updates.Message;
import me.shib.java.lib.jtelebot.models.updates.Update;
import me.shib.java.lib.jtelebot.service.TelegramBot;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
       TelegramBot bot = TelegramBot.getInstance(Settings.TelegramToken);
        Update[] updates;
        while((updates = bot.getUpdates()) != null) {
            for (Update update : updates) {
                Message message = update.getMessage();

                if(message != null) {
                    MessageAssistant.doSmthng(message);
                }
            }
        }
    }
}

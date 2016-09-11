package com.company;

import me.shib.java.lib.jtelebot.models.types.ChatId;
import me.shib.java.lib.jtelebot.models.updates.Message;
import me.shib.java.lib.jtelebot.service.TelegramBot;

import java.io.IOException;

/**
 * Created by sha on 04.09.2016.
 */
public  class MessageAssistant {
    static TelegramBot bot = TelegramBot.getInstance(Settings.TelegramToken);
    //RU001251570HK

    static void doSmthng (Message message) throws IOException {
        System.out.println(message.getText());
        if(message.getText()==null) return;
        if (message.getText().equals("/start")){
           bot.sendMessage(new ChatId(message.getChat().getId()), "Здравствуйте, это бот для отлеживаний почтовых отправлений. " +
                   "Чтобы начать, введите трек-номер (баркод). ");
        }
        if (message.getText().toLowerCase().matches("^[a-z]{2}[0-9]{9}[a-z]{2}$")){
            bot.sendMessage(new ChatId(message.getChat().getId()),"Международное отправление. "+ message.getText());
            RusPost rp = new RusPost();
            rp.setCode(message.getText());
            bot.sendMessage(new ChatId(message.getChat().getId()),rp.connect());

        }
        //42325001024869
        if (message.getText().toLowerCase().matches("^[0-9]{14}$")){
            bot.sendMessage(new ChatId(message.getChat().getId()),"Российское отправление. " + message.getText());
            RusPost rp = new RusPost();
            rp.setCode(message.getText());
            bot.sendMessage(new ChatId(message.getChat().getId()),rp.connect());
        }
        }
}

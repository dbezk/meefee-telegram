package com.meefee.telegram.bot.handler;

import com.meefee.telegram.bot.AuthorizationBot;
import com.meefee.telegram.bot.constant.BotCommands;
import com.meefee.telegram.bot.constant.BotMessages;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CommandHandler {

    public void handleCommand(AuthorizationBot authorizationBot, Message message) {
        if(message.getText().equals(BotCommands.START_COMMAND)) {
            var messageText = String.format(BotMessages.START_COMMAND, message.getFrom().getFirstName());
            authorizationBot.sendMessage(message.getChatId(), messageText);
        } else {
            authorizationBot.sendMessage(message.getChatId(), BotMessages.UNKNOWN_COMMAND);
        }
    }

}

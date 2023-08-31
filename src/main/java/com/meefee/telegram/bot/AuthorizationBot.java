package com.meefee.telegram.bot;

import com.meefee.telegram.bot.constant.BotMessages;
import com.meefee.telegram.bot.handler.CommandHandler;
import com.meefee.telegram.bot.handler.TextHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class AuthorizationBot extends TelegramLongPollingBot {

    private final TextHandler textHandler;
    private final CommandHandler commandHandler;

    @Value(value = "${bot.username}")
    private String botUsername;

    public AuthorizationBot(@Value(value = "${bot.token}") String botToken,
                            TextHandler textHandler,
                            CommandHandler commandHandler) {
        super(botToken);
        this.textHandler = textHandler;
        this.commandHandler = commandHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            var message = update.getMessage();
            if(message.isCommand()) {
                commandHandler.handleCommand(this, message);
            } else if(message.hasText()) {
                textHandler.handleText(this, message);
            } else {
                sendMessage(message.getChatId(), BotMessages.UNKNOWN_MESSAGE_TYPE);
            }
        }
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage newMessage = new SendMessage();
        newMessage.setChatId(chatId);
        newMessage.setText(text);
        newMessage.enableHtml(true);
        try {
            execute(newMessage);
        } catch (TelegramApiException e) {
            log.error("Error when sending message to chat id = {}", chatId);
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}

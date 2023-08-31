package com.meefee.telegram.bot.handler;

import com.meefee.telegram.bot.AuthorizationBot;
import com.meefee.telegram.bot.constant.BotMessages;
import com.meefee.telegram.kafka.AuthorizationProducer;
import com.meefee.telegram.service.AuthorizationDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class TextHandler {

    private final AuthorizationDataService authorizationDataService;

    private final AuthorizationProducer authorizationProducer;

    public void handleText(AuthorizationBot authorizationBot, Message message) {
        if(isCodeValid(message.getText())) {
            var code = Integer.parseInt(message.getText());
            if(authorizationDataService.isValidAuthorizationData(code)) {
                authorizationDataService.setAuthorizationDataByCode(code, message.getFrom().getId(),
                        message.getFrom().getFirstName(), message.getFrom().getLastName());
                authorizationProducer.sendSuccessfulAuthorization(authorizationDataService.findAuthorizationDataByCode(code));
                authorizationDataService.deleteAuthorizationDataByCode(code);
            } else {
                authorizationBot.sendMessage(message.getChatId(), BotMessages.AUTHORIZATION_CODE_NOT_FOUND_OR_INVALID);
            }
        } else {
            authorizationBot.sendMessage(message.getChatId(), BotMessages.INVALID_AUTHORIZATION_CODE);
        }
    }

    public boolean isCodeValid(String text) {
        return text.matches("^[0-9]{8}$");
    }

}

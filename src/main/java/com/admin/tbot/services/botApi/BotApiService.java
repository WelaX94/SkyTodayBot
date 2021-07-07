package com.admin.tbot.services.botApi;

import com.admin.tbot.botLogics.BotStatusHandler;
import com.admin.tbot.models.InputMessage;
import com.admin.tbot.models.User;
import com.admin.tbot.models.WebHookRegistration;
import com.admin.tbot.repositories.UserListRepository;
import com.admin.tbot.repositories.botApi.BotApiRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Getter
@Setter
public class BotApiService extends TelegramWebhookBot implements BotApiRepository {

    @Value("${telegramBot.name}")
    private String name;
    @Value("${telegramBot.token}")
    private String token;
    @Value("${telegramBot.webHookPath}")
    private String webHookPath;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private BotStatusHandler botStatusHandler;
    @Autowired
    private BotApiButtons botApiButtons;
    @Autowired
    private UserListRepository userListRepository;

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.getMessage() != null) {
            final InputMessage inputMessage = new InputMessage(update.getMessage());
            final SendMessage sendMessage = new SendMessage();
            final User user = userListRepository.getUser(inputMessage.getChatId());
            sendMessage.setText(botStatusHandler.handleInputMessage(inputMessage, user));
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setReplyMarkup(botApiButtons.setButtons(user));
            userListRepository.updateUserList(user);
            return sendMessage;
        } else {
            return null;
        }
    }

    @Override
    public boolean registerWebHook() {
        try {
            final String registerWebHookUrl = "https://api.telegram.org/bot" + token + "/setWebhook?url=" + webHookPath;
            final ResponseEntity<WebHookRegistration> responseEntity = restTemplate.exchange
                    (registerWebHookUrl, HttpMethod.GET, null, new ParameterizedTypeReference<WebHookRegistration>() {
                    });
            final WebHookRegistration answer = responseEntity.getBody();
            return answer.isOk() && answer.isResult();
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    @Override
    public void sendMessage(String message, long chatId) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);

        try {
            executeSendMessage(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e);
        }
    }

    private void executeSendMessage(SendMessage sendMessage) throws TelegramApiException {
        execute(sendMessage);
    }

}
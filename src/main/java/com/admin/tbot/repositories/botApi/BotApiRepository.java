package com.admin.tbot.repositories.botApi;

import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Repository
public interface BotApiRepository {

    public boolean registerWebHook();

    public void sendMessage(String message, long chatId);

    public BotApiMethod<?> onWebhookUpdateReceived(Update update);

}

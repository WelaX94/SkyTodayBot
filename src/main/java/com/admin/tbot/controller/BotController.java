package com.admin.tbot.controller;

import com.admin.tbot.repositories.botApi.BotApiRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@Getter
@Setter
public class BotController {

    @Autowired
    private BotApiRepository botApiRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return botApiRepository.onWebhookUpdateReceived(update);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String checkApplication() {
        return "The application is running";
    }

}
package com.admin.tbot.models;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Message;

@Getter
@Setter
public class InputMessage {
    final long chatId;
    private String text;
    private Coordinates coordinates;

    public InputMessage(long chatId) {
        this.chatId = chatId;
    }

    public InputMessage(Message message) {
        this.chatId = message.getChatId();
        if (message.hasText()) this.text = message.getText();
        if (message.hasLocation()) {
            this.coordinates = new Coordinates(message.getLocation().getLatitude(), message.getLocation().getLongitude());
        }
    }

    public boolean hasText() {
        return !(text == null);
    }

    public boolean hasCoordinates() {
        return !(coordinates == null);
    }

}
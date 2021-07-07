package com.admin.tbot.botLogics;

import com.admin.tbot.botLogics.enums.EmojiList;
import com.admin.tbot.models.InputMessage;
import com.admin.tbot.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public class BotStatusHandler {

    @Autowired
    private ReplyMessagesToRegistrationNewUser replyMessagesToRegistrationNewUser;

    @Autowired
    private ReplyMessagesToExistingUser replyMessagesToExistingUser;

    public String handleInputMessage(InputMessage inputMessage, User user) {
        String replyMessage;
        if (user.isRegistrationComplete()) {
            replyMessage = replyToAnExistingUser(inputMessage, user);

        } else {
            replyMessage = replyToRegistrationNewUser(inputMessage, user);
        }
        return replyMessage;
    }

    private String replyToAnExistingUser(InputMessage inputMessage, User user) {
        String replyMessage;
        switch (user.getBotStatus()) {
            case MAIN_MENU:
                if (inputMessage.hasText()) replyMessage = replyMessagesToExistingUser.replyMainMenu(inputMessage, user);
                else replyMessage = replyMessagesToExistingUser.UnknownCommand();
                break;
            case GET_WEATHER:
                if (inputMessage.hasText()) replyMessage = replyMessagesToExistingUser.replyGetWeather(inputMessage, user);
                else replyMessage = replyMessagesToExistingUser.UnknownCommand();
                break;
            case SETTINGS:
                if (inputMessage.hasText()) replyMessage = replyMessagesToExistingUser.replySettings(inputMessage, user);
                else replyMessage = replyMessagesToExistingUser.UnknownCommand();
                break;
            case CITY_CHANGE:
                replyMessage = replyMessagesToExistingUser.replyCityChange(inputMessage, user);
                break;
            case CURRENT_WEATHER_SELECTION:
                if (inputMessage.hasText())
                    replyMessage = replyMessagesToExistingUser.replyCurrentWeatherSelection(inputMessage, user);
                else replyMessage = replyMessagesToExistingUser.UnknownCommand();
                break;
            case HOURLY_FORECAST_WEATHER_SELECTION:
                if (inputMessage.hasText())
                    replyMessage = replyMessagesToExistingUser.replyHourlyForecastWeatherSelection(inputMessage, user);
                else replyMessage = replyMessagesToExistingUser.UnknownCommand();
                break;
            case ENTER_TIME:
                if (inputMessage.hasText()) replyMessage = replyMessagesToExistingUser.replyEnterTime(inputMessage, user);
                else replyMessage = replyMessagesToExistingUser.UnknownCommand();
                break;
            default:
                replyMessage = "Неизвестное состояние бота";
        }
        return replyMessage;
    }

    private String replyToRegistrationNewUser(InputMessage inputMessage, User user) {
        String replyMessage;
        switch (user.getBotStatus()) {
            case START:
                replyMessage = replyMessagesToRegistrationNewUser.Start(user);
                break;
            case ASK_CITY:
                replyMessage = replyMessagesToRegistrationNewUser.AskCity(inputMessage, user);
                break;
            case ASK_DAILY_MAILING:
                if (inputMessage.hasText())
                    replyMessage = replyMessagesToRegistrationNewUser.AskDailyMailing(inputMessage, user);
                else
                    replyMessage = EmojiList.CHECK_NO.get() + " Ошибка установки настроек рассылки. Воспользуйтесь, пожалуйста, кнопками меню. Нужна ли вам ежедневная рассылка погоды?";
                break;
            default:
                replyMessage = "Неизвестное состояние бота";
        }
        return replyMessage;
    }

}
package com.admin.tbot.botLogics;

import com.admin.tbot.botLogics.convertation.Converting;
import com.admin.tbot.botLogics.enums.BotStatus;
import com.admin.tbot.botLogics.enums.EmojiList;
import com.admin.tbot.models.City;
import com.admin.tbot.models.InputMessage;
import com.admin.tbot.models.User;
import com.vdurmont.emoji.EmojiParser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Getter
@Setter
public class ReplyMessagesToExistingUser {

    @Autowired
    private WeatherMessages weatherMessages;

    public String replyGetWeather(InputMessage inputMessage, User user) {
        String replyMessage;
        final City city = user.getCityInfo();
        switch (inputMessage.getText()) {
            case "Текущая погода":
                replyMessage = "Выберите тип прогноза";
                user.setBotStatus(BotStatus.CURRENT_WEATHER_SELECTION);
                break;
            case "Почасовой прогноз на сутки":
                replyMessage = "Выберите шаг";
                user.setBotStatus(BotStatus.HOURLY_FORECAST_WEATHER_SELECTION);
                break;
            case "Прогноз на неделю":
                replyMessage = weatherMessages.getDailyForecast(city);
                break;
            case "Вернуться в главное меню":
                replyMessage = "Главное меню";
                user.setBotStatus(BotStatus.MAIN_MENU);
                break;
            default:
                replyMessage = "Неизвестная команда. Пожалуйста, воспользуйтесь кнопками главного меню";
                break;
        }
        return replyMessage;
    }

    public String replyCurrentWeatherSelection(InputMessage inputMessage, User user) {
        String replyMessage;
        final City city = user.getCityInfo();
        switch (inputMessage.getText()) {
            case "Вкратце":
                replyMessage = weatherMessages.getCurrentWeather(city);
                user.setBotStatus(BotStatus.GET_WEATHER);
                break;
            case "Подробно":
                replyMessage = weatherMessages.getDetailedCurrentWeather(city);
                user.setBotStatus(BotStatus.GET_WEATHER);
                break;
            case "Назад":
                replyMessage = "Что вас интересует?";
                user.setBotStatus(BotStatus.GET_WEATHER);
                break;
            default:
                replyMessage = "Неизвестная команда. Пожалуйста, воспользуйтесь кнопками главного меню";
                break;
        }
        return replyMessage;
    }

    public String replyHourlyForecastWeatherSelection(InputMessage inputMessage, User user) {
        String replyMessage;
        final City city = user.getCityInfo();
        switch (inputMessage.getText()) {
            case "На каждый час":
                replyMessage = weatherMessages.getHourlyForecast(city, 1);
                user.setBotStatus(BotStatus.GET_WEATHER);
                break;
            case "На каждые 3 часа":
                replyMessage = weatherMessages.getHourlyForecast(city, 3);
                user.setBotStatus(BotStatus.GET_WEATHER);
                break;
            case "Назад":
                replyMessage = "Что вас интересует?";
                user.setBotStatus(BotStatus.GET_WEATHER);
                break;
            default:
                replyMessage = "Неизвестная команда. Пожалуйста, воспользуйтесь кнопками главного меню";
                break;
        }
        return replyMessage;
    }

    public String replyMainMenu(InputMessage inputMessage, User user) {
        String replyMessage;
        switch (inputMessage.getText()) {
            case "Узнать погоду":
                replyMessage = "Что вас интересует?";
                user.setBotStatus(BotStatus.GET_WEATHER);
                break;
            case "Мои настройки":
                replyMessage = createInfoMessage(user);
                user.setBotStatus(BotStatus.SETTINGS);
                break;
            case "Информация о боте":
                replyMessage = "SkyTodayBot поможет вам узнать погоду как текущую, так и на ближайшее время. " +
                        "Для навигации просто используйте кнопки меню. Также данный бот умеет раз в день присылать вам прогноз погоды на сегодня и завтра в любое удобное время. " +
                        "В качестве источника используется открытое API от OpenWeather:\nhttps://openweathermap.org/\n" +
                        "Бот работает в тестовом режиме. По всем вопросам, связанным с ним, можете обращаться по адресу:\n" +
                        "welax94@gmail.com";
                break;
            default:
                replyMessage = "Неизвестная команда. Пожалуйста, воспользуйтесь кнопками главного меню";
                break;
        }
        return replyMessage;
    }

    public String replySettings(InputMessage inputMessage, User user) {
        String replyMessage;
        switch (inputMessage.getText()) {
            case "Изменить время оповещения":
                user.setBotStatus(BotStatus.ENTER_TIME);
                replyMessage = "Пожалуйста, введите время в формате ЧЧ:ММ (например 13:45 или 08:30)";
                break;
            case "Отключить ежедневное оповещение":
                user.setDailyMailing(false);
                replyMessage = "Ежедневная рассылка отключена. Ваши текущие настройки:" + '\n' + createInfoMessage(user);
                break;
            case "Включить ежедневное оповещение":
                replyMessage = "Пожалуйста, введите время в формате ЧЧ:ММ (например 13:45 или 08:30)";
                user.setBotStatus(BotStatus.ENTER_TIME);
                break;
            case "Изменить город":
                replyMessage = "Пожалуйста, укажите ваш новый город";
                user.setBotStatus(BotStatus.CITY_CHANGE);
                break;
            case "Вернуться в главное меню":
                replyMessage = "Главное меню";
                user.setBotStatus(BotStatus.MAIN_MENU);
                break;
            default:
                replyMessage = "Неизвестная команда. Пожалуйста, воспользуйтесь кнопками главного меню";
                break;
        }
        return replyMessage;
    }

    public String replyCityChange(InputMessage inputMessage, User user) {
        String replyMessage;
        if (inputMessage.hasText() && inputMessage.getText().equals("Отмена")) {
            replyMessage = createInfoMessage(user);
            user.setBotStatus(BotStatus.SETTINGS);
        } else if (citySet(inputMessage, user)) {
            replyMessage = EmojiList.CHECK_YES.get() + " Город успешно изменён. Ваши текущие настройки:" + '\n' + createInfoMessage(user);
            user.setBotStatus(BotStatus.SETTINGS);
        } else {
            replyMessage = EmojiList.CHECK_NO.get() + " Город не найден. Проверьте правильность его написания";
        }
        return replyMessage;
    }

    public String replyEnterTime(InputMessage inputMessage, User user) {
        String replyMessage;
        if (inputMessage.getText().equals("Отмена")) {
            user.setBotStatus(BotStatus.SETTINGS);
            replyMessage = createInfoMessage(user);
        } else {
            final Pattern correctlyTime = Pattern.compile("([0-1][0-9]|2[0-3]):[0-5][0-9]");
            boolean checkTime = correctlyTime.matcher(inputMessage.getText()).matches();
            if (checkTime) {
                LocalTime notificationTime = Converting.stringTimeToLocalTime(inputMessage.getText());
                user.setDailyMailing(true);
                user.setNewNotificationTime(notificationTime);
                user.setBotStatus(BotStatus.SETTINGS);
                replyMessage = EmojiList.CHECK_YES.get() + " Настройки уведомлений успешно изменены. Ваши текущие настройки:\n" + createInfoMessage(user);
            } else {
                replyMessage = EmojiList.CHECK_NO.get() + " Время задано неверно. Пожалуйста, введите время в формате ЧЧ:ММ (например 13:45 или 08:30)";
            }
        }
        return replyMessage;
    }

    public String UnknownCommand() {
        return "Неизвестная команда. Пожалуйста, следуйте инструкциям или воспользуйтесь кнопками главного меню";
    }

    public boolean citySet(InputMessage inputMessage, User user) {
        boolean result = false;
        City city;
        try {
            if (inputMessage.hasText()) {
                city = weatherMessages.checkCity(inputMessage.getText());
                user.setCityInfo(city);
                result = true;
            } else if (inputMessage.hasCoordinates()) {
                city = weatherMessages.checkCity(inputMessage.getCoordinates());
                user.setCityInfo(city);
                result = true;
            }
            return result;
        } catch (HttpClientErrorException e) {
            return result;
        }
    }

    private String createInfoMessage(User user) {
        String mailingInfo;
        if (user.isDailyMailing()) {
            mailingInfo = "Включено (" + user.getNotificationTime().format(DateTimeFormatter.ofPattern("HH:mm")) + ") " + EmojiList.MAILBOX_FULL.get();
        } else {
            mailingInfo = "Отключено " + EmojiList.MAILBOX_EMPTY.get();
        }
        return "Ваш город: " + user.getCity() + " " +
                EmojiParser.parseToUnicode(user.getCountry()) + '\n' +
                "Ежедневное оповещение: " + mailingInfo;
    }

}

package com.admin.tbot.services.botApi;

import com.admin.tbot.models.User;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BotApiButtons {

    public ReplyKeyboard setButtons(User user) {
        switch (user.getBotStatus()) {
            case ASK_CITY:
                return setAskCity();
            case ASK_DAILY_MAILING:
                return setAskDailyMailing();
            case MAIN_MENU:
                return setMainMenu();
            case GET_WEATHER:
                return setGetWeather();
            case SETTINGS:
                return setSettings(user);
            case CITY_CHANGE:
                return setCityChange();
            case CURRENT_WEATHER_SELECTION:
                return setCurrentWeatherSelection();
            case HOURLY_FORECAST_WEATHER_SELECTION:
                return setHourlyForecastWeatherSelectionSelection();
            case ENTER_TIME:
                return setEnterTime();
            default:
                return clearButtons();
        }
    }

    private ReplyKeyboard setAskCity() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        final List<KeyboardRow> keyboardRowList = new ArrayList<>();

        final KeyboardRow row1 = new KeyboardRow();
        final KeyboardButton location = new KeyboardButton("Отправить гео-позицию");
        location.setRequestLocation(true);
        row1.add(location);

        keyboardRowList.add(row1);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboard setAskDailyMailing() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        final List<KeyboardRow> keyboardRowList = new ArrayList<>();

        final KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Да"));
        row1.add(new KeyboardButton("Нет"));
        keyboardRowList.add(row1);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboard setMainMenu() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        final List<KeyboardRow> keyboardRowList = new ArrayList<>();

        final KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Узнать погоду"));
        final KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Мои настройки"));
        final KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("Информация о боте"));

        keyboardRowList.add(row1);
        keyboardRowList.add(row2);
        keyboardRowList.add(row3);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboard setGetWeather() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        final KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Текущая погода"));
        final KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Почасовой прогноз на сутки"));
        final KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("Прогноз на неделю"));
        final KeyboardRow row4 = new KeyboardRow();
        row4.add(new KeyboardButton("Вернуться в главное меню"));

        keyboardRowList.add(row1);
        keyboardRowList.add(row2);
        keyboardRowList.add(row3);
        keyboardRowList.add(row4);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboard setSettings(User user) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        final List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Изменить город"));
        keyboardRowList.add(row1);

        if (user.isDailyMailing()) {
            KeyboardRow row2 = new KeyboardRow();
            row2.add(new KeyboardButton("Изменить время оповещения"));
            keyboardRowList.add(row2);
            KeyboardRow row3 = new KeyboardRow();
            row3.add(new KeyboardButton("Отключить ежедневное оповещение"));
            keyboardRowList.add(row3);
        } else {
            KeyboardRow row2 = new KeyboardRow();
            row2.add(new KeyboardButton("Включить ежедневное оповещение"));
            keyboardRowList.add(row2);
        }

        KeyboardRow row4 = new KeyboardRow();
        row4.add(new KeyboardButton("Вернуться в главное меню"));
        keyboardRowList.add(row4);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboard setCityChange() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        final List<KeyboardRow> keyboardRowList = new ArrayList<>();

        final KeyboardRow row1 = new KeyboardRow();
        KeyboardButton location = new KeyboardButton("Отправить гео-позицию");
        location.setRequestLocation(true);
        row1.add(location);
        final KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Отмена"));

        keyboardRowList.add(row1);
        keyboardRowList.add(row2);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboard setCurrentWeatherSelection() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        final List<KeyboardRow> keyboardRowList = new ArrayList<>();

        final KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Вкратце"));
        row1.add(new KeyboardButton("Подробно"));
        final KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Назад"));

        keyboardRowList.add(row1);
        keyboardRowList.add(row2);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboard setHourlyForecastWeatherSelectionSelection() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        final List<KeyboardRow> keyboardRowList = new ArrayList<>();

        final KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("На каждый час"));
        row1.add(new KeyboardButton("На каждые 3 часа"));
        final KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Назад"));

        keyboardRowList.add(row1);
        keyboardRowList.add(row2);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboard setEnterTime() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        final List<KeyboardRow> keyboardRowList = new ArrayList<>();

        final KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Отмена"));

        keyboardRowList.add(row1);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboard clearButtons() {
        return new ReplyKeyboardRemove();
    }

}
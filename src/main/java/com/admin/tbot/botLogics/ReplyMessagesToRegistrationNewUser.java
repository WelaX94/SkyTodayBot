package com.admin.tbot.botLogics;

import com.admin.tbot.botLogics.enums.BotStatus;
import com.admin.tbot.botLogics.enums.EmojiList;
import com.admin.tbot.models.InputMessage;
import com.admin.tbot.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public class ReplyMessagesToRegistrationNewUser {

    @Autowired
    private ReplyMessagesToExistingUser replyMessagesToExistingUser;

    public String Start(User user) {
        String replyMessage;
        replyMessage = "Здравстуйте. Для начала введите названия города, нажмите на кнопку, что-бы отправить вашу геопозицию, или отправьте геопозицию вручную";
        user.setBotStatus(BotStatus.ASK_CITY);
        return replyMessage;
    }

    public String AskCity(InputMessage inputMessage, User user) {
        String replyMessage;
        if (replyMessagesToExistingUser.citySet(inputMessage, user)) {
            replyMessage = EmojiList.CHECK_YES.get() + " Отлично, город установлен! Теперь скажите, нужна ли вам ежедневная рассылка погоды? По умолчанию время будет установлено на 10:00, но это в дальнейшем можно поменять в настройках.";
            user.setBotStatus(BotStatus.ASK_DAILY_MAILING);
        } else {
            replyMessage = EmojiList.CHECK_NO.get() + " Город не найден. Проверьте правильность его написания и попробуйте ввести название города ещё раз.";
        }
        return replyMessage;
    }

    public String AskDailyMailing(InputMessage inputMessage, User user) {
        String replyMessage;
        if (inputMessage.getText().equals("Да") || inputMessage.getText().equals("Нет")) {
            if (inputMessage.getText().equals("Да")) {
                user.setDailyMailing(true);
                user.setDefaultNotificationTime();
            } else {
                user.setDailyMailing(false);
            }
            replyMessage = EmojiList.CHECK_YES.get() + " Отлично, настройки рассылки установлены! Теперь вы можете воспользоваться главным меню.";
            user.setBotStatus(BotStatus.MAIN_MENU);
            user.setRegistrationComplete(true);
        } else {
            replyMessage = EmojiList.CHECK_NO.get() + " Ошибка установки настроек рассылки. Воспользуйтесь, пожалуйста, кнопками. Нужна ли вам ежедневная рассылка погоды?";
        }
        return replyMessage;
    }

}
package com.admin.tbot.botLogics.scheduledTasks;

import com.admin.tbot.repositories.botApi.BotApiRepository;
import com.admin.tbot.botLogics.WeatherMessages;
import com.admin.tbot.models.User;
import com.admin.tbot.repositories.UserListRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EnableScheduling
@Component("dailyNotification")
public class DailyNotification {

    @Autowired
    private UserListRepository userListRepository;

    @Autowired
    private WeatherMessages weatherMessages;

    @Autowired
    private BotApiRepository botApiRepository;

    @Scheduled(fixedRate = 60000)
    public void notificationCheck() {
        final List<User> userList = userListRepository.getDailyMailingUsers();
        final List<User> updateList = new ArrayList<>();
        for (User user: userList) {
            final LocalDateTime now = LocalDateTime.now(ZoneId.of(user.getUtc()));
            if (user.isDailyMailing() && user.getNotificationTime().isBefore(now)) {
                final long chatId = (user.getChatId());
                String message = weatherMessages.getDailyNotification(user.getCityInfo());
                user.setNotificationTime(user.getNotificationTime().plusDays(1));
                updateList.add(user);
                botApiRepository.sendMessage(message, chatId);
            }
        }
        if (!updateList.isEmpty()) userListRepository.updateUserList(updateList);
    }

}
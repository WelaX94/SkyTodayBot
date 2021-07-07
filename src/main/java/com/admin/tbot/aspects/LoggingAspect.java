package com.admin.tbot.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Aspect
public class LoggingAspect {

    @Before("newInputMessage()")
    public void beforeProcessingTheMessageAdvice(JoinPoint joinPoint) {
        final LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Europe/Kiev"));
        final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy  HH:mm:ss");

        Update update = (Update) joinPoint.getArgs()[0];
        if (update.getMessage() != null) {
            Message message = update.getMessage();
            System.out.println(ldt.format(timeFormat) + "  Input message from " + message.getFrom().getFirstName() + " " + message.getFrom().getLastName() + " (" + message.getFrom().getUserName() + ")" + ", chat id: " + message.getChatId() + ". Message content - " + getMessageContent(message));
        } else {
            System.out.println(ldt.format(timeFormat) + "  Input empty message");
        }
    }

    @AfterReturning("newInputMessage()")
    public void afterReturningProcessingTheMessageAdvice() {
        final LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Europe/Kiev"));
        final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy  HH:mm:ss");
        System.out.println(ldt.format(timeFormat) + "  Message processing completed successfully");
    }

    @Before("dailyNotificationChecking()")
    public void beforeDailyNotificationCheckingAdvice() {
        final LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Europe/Kiev"));
        final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy  HH:mm:ss");
        System.out.println(ldt.format(timeFormat) + "  Weather mailing list check started");
    }

    @AfterReturning("dailyNotificationChecking()")
    public void afterReturningNotificationCheckingAdvice() {
        final LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Europe/Kiev"));
        final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy  HH:mm:ss");
        System.out.println(ldt.format(timeFormat) + "  Finished checking weather mailing list");
    }

    @AfterReturning("messageSending()")
    public void afterReturningExecuteSendMessageAdvice(JoinPoint joinPoint) {
        SendMessage sendMessage = (SendMessage) joinPoint.getArgs()[0];
        final LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Europe/Kiev"));
        final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy  HH:mm:ss");
        System.out.println(ldt.format(timeFormat) + "  Successful message sending (chat id: " + sendMessage.getChatId() + ")");
    }

    @AfterThrowing("messageSending()")
    public void afterThrowingExecuteSendMessageAdvice(JoinPoint joinPoint) {
        SendMessage sendMessage = (SendMessage) joinPoint.getArgs()[0];
        final LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Europe/Kiev"));
        final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy  HH:mm:ss");
        System.out.println(ldt.format(timeFormat) + "  Message sending failed (chat id: " + sendMessage.getChatId() + ")");
    }

    @Pointcut("execution(public org.telegram.telegrambots.meta.api.methods.BotApiMethod com.admin.tbot.services.botApi.BotApiService.onWebhookUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update))")
    private void newInputMessage() {
    }

    @Pointcut("execution(public void com.admin.tbot.botLogics.scheduledTasks.DailyNotification.notificationCheck())")
    private void dailyNotificationChecking() {
    }

    @Pointcut("execution(private void com.admin.tbot.services.botApi.BotApiService.executeSendMessage(org.telegram.telegrambots.meta.api.methods.send.SendMessage) throws org.telegram.telegrambots.meta.exceptions.TelegramApiException)")
    private void messageSending() {
    }

    private String getMessageContent(Message message) {
        String content = "";
        if (message.hasText()) content += "text(" + message.getText() + ")  ";
        if (message.hasLocation()) content += "location  ";
        if (message.hasAnimation()) content += "animation  ";
        if (message.hasContact()) content += "contact  ";
        if (message.hasAudio()) content += "audio  ";
        if (message.hasDocument()) content += "document  ";
        if (message.hasEntities()) content += "entities  ";
        if (message.hasInvoice()) content += "invoice  ";
        if (message.hasPassportData()) content += "passport data  ";
        if (message.hasPhoto()) content += "photo  ";
        if (message.hasPoll()) content += "poll  ";
        if (message.hasReplyMarkup()) content += "reply markup  ";
        if (message.hasSticker()) content += "sticker  ";
        if (message.hasSuccessfulPayment()) content += "successfully payment  ";
        if (message.hasVideo()) content += "video  ";
        if (message.hasVideoNote()) content += "video note  ";
        if (message.hasVoice()) content += "voice";
        return content;
    }

}
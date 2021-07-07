package com.admin.tbot.botLogics.convertation;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.TimeZone;

public class Converting {

    public static String unixToStringDate(long unix) {
        final Date date = new Date(unix * 1000);
        final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        format.setTimeZone(TimeZone.getTimeZone("GMT0"));
        return format.format(date);
    }

    public static String unixToStringShortDate(long unix) {
        final Date date = new Date(unix * 1000);
        final SimpleDateFormat format = new SimpleDateFormat("dd.MM");
        format.setTimeZone(TimeZone.getTimeZone("GMT0"));
        return format.format(date);
    }

    public static String unixToStringTime(long unix) {
        final Date date = new Date(unix * 1000);
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT0"));
        return format.format(date);
    }

    public static LocalTime stringTimeToLocalTime(String time) {
        final int hour = Integer.parseInt(time.substring(0, 2));
        final int minute = Integer.parseInt(time.substring(3));
        return LocalTime.of(hour, minute);
    }

    public static String windDegreeToDirectionDetailed(int degree) {
        if (degree < 11) return "северный";
        else if (degree < 34) return "северо-северо-восточный";
        else if (degree < 56) return "северо-восточный";
        else if (degree < 79) return "восточный-северо-восточный";
        else if (degree < 101) return "восточный";
        else if (degree < 124) return "восточный-юго-восточный";
        else if (degree < 146) return "юго-восточный";
        else if (degree < 167) return "юго-юго-восточный";
        else if (degree < 191) return "южный";
        else if (degree < 214) return "юго-юго-западный";
        else if (degree < 236) return "юго-западный";
        else if (degree < 259) return "западный-юго-западный";
        else if (degree < 281) return "западный";
        else if (degree < 304) return "западный-северо-западный";
        else if (degree < 326) return "северо-западный";
        else if (degree < 349) return "северо-северо-западный";
        else if (degree < 360) return "северный";
        else return "неизвестное направление";
    }

    public static String windDegreeToDirectionSimple(int degree) {
        if (degree < 23) return "северный";
        else if (degree < 68) return "северо-восточный";
        else if (degree < 113) return "восточный";
        else if (degree < 158) return "юго-восточный";
        else if (degree < 203) return "южный";
        else if (degree < 248) return "юго-западный";
        else if (degree < 293) return "западный";
        else if (degree < 338) return "северо-западный";
        else if (degree < 360) return "северный";
        else return "неизвестное направление";
    }

}
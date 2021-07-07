package com.admin.tbot.botLogics.convertation;

import com.admin.tbot.botLogics.enums.EmojiList;

public class DataToEmoji {

    public static String convertWeather(int weatherId) {
        switch (weatherId / 100) {
            case 2:
                return weatherConvertGroup2(weatherId);
            case 3:
                return weatherConvertGroup3();
            case 5:
                return weatherConvertGroup5();
            case 6:
                return weatherConvertGroup6(weatherId);
            case 7:
                return weatherConvertGroup7(weatherId);
            case 8:
                return weatherConvertGroup8(weatherId);
            default:
                return "Неизвестный ID";
        }
    }

    public static String convertTime(long unix) {
        String time = Converting.unixToStringTime(unix);
        switch (time) {
            case "00:00":
            case "12:00":
                return EmojiList.CLOCK_12.get();
            case "01:00":
            case "13:00":
                return EmojiList.CLOCK_1.get();
            case "02:00":
            case "14:00":
                return EmojiList.CLOCK_2.get();
            case "03:00":
            case "15:00":
                return EmojiList.CLOCK_3.get();
            case "04:00":
            case "16:00":
                return EmojiList.CLOCK_4.get();
            case "05:00":
            case "17:00":
                return EmojiList.CLOCK_5.get();
            case "06:00":
            case "18:00":
                return EmojiList.CLOCK_6.get();
            case "07:00":
            case "19:00":
                return EmojiList.CLOCK_7.get();
            case "08:00":
            case "20:00":
                return EmojiList.CLOCK_8.get();
            case "09:00":
            case "21:00":
                return EmojiList.CLOCK_9.get();
            case "10:00":
            case "22:00":
                return EmojiList.CLOCK_10.get();
            case "11:00":
            case "23:00":
                return EmojiList.CLOCK_11.get();
            default:
                return "Неизвестное время";
        }
    }

    public static String convertWindDegree(int degree) {
        if (degree < 23) return EmojiList.ARROW_DOWN.get();
        else if (degree < 68) return EmojiList.ARROW_LOWER_LEFT.get();
        else if (degree < 113) return EmojiList.ARROW_LEFT.get();
        else if (degree < 158) return EmojiList.ARROW_UPPER_LEFT.get();
        else if (degree < 203) return EmojiList.ARROW_UP.get();
        else if (degree < 248) return EmojiList.ARROW_UPPER_RIGHT.get();
        else if (degree < 293) return EmojiList.ARROW_RIGHT.get();
        else if (degree < 338) return EmojiList.ARROW_LOWER_RIGHT.get();
        else if (degree < 360) return EmojiList.ARROW_DOWN.get();
        else return "неизвестное направление";
    }

    private static String weatherConvertGroup2(int weatherId) {
        String result;
        switch (weatherId) {
            case 200:
            case 201:
            case 202:
            case 230:
            case 231:
            case 232:
                result = EmojiList.WEATHER_THUNDER_WITH_RAIN.get();
                result += result + result;
                break;
            case 210:
            case 211:
            case 212:
            case 221:
                result = EmojiList.WEATHER_LIGHTING.get();
                result += result + result;
                break;
            default:
                result = "Неизвестный ID";
        }
        return result;
    }

    private static String weatherConvertGroup3() {
        String result = EmojiList.WEATHER_RAIN.get();
        result += result + result;
        return result;
    }

    private static String weatherConvertGroup5() {
        String result = EmojiList.WEATHER_RAIN.get();
        result += result + result;
        return result;
    }

    private static String weatherConvertGroup6(int weatherId) {
        String result;
        switch (weatherId) {
            case 600:
            case 601:
            case 602:
            case 611:
            case 620:
            case 622:
                result = EmojiList.WEATHER_SNOW.get();
                result += result + result;
                break;
            case 612:
            case 613:
            case 615:
            case 616:
            case 621:
                result = EmojiList.WEATHER_SNOW.get() + EmojiList.WEATHER_RAIN.get() + EmojiList.WEATHER_SNOW.get();
                break;
            default:
                result = "Неизвестный ID";
        }
        return result;
    }

    private static String weatherConvertGroup7(int weatherId) {
        String result;
        switch (weatherId) {
            case 701:
            case 711:
            case 721:
            case 741:
            case 751:
            case 761:
            case 762:
                result = EmojiList.WEATHER_FOG.get();
                result += result + result;
                break;
            case 731:
                result = EmojiList.WEATHER_FOG.get() + EmojiList.WEATHER_DASH.get() + EmojiList.WEATHER_FOG.get();
                break;
            case 771:
                result = EmojiList.WEATHER_DASH.get();
                result += result + result;
                break;
            case 781:
                result = EmojiList.WEATHER_TORNADO.get();
                result += result + result;
                break;
            default:
                result = "Неизвестный ID";
        }
        return result;
    }

    private static String weatherConvertGroup8(int weatherId) {
        String result;
        switch (weatherId) {
            case 800:
                result = EmojiList.WEATHER_SUNNY.get();
                result += result + result;
                break;
            case 801:
                result = EmojiList.WEATHER_SMALL_CLOUDS.get();
                result += result + result;
                break;
            case 802:
                result = EmojiList.WEATHER_PARTLY_SUNNY.get();
                result += result + result;
                break;
            case 803:
                result = EmojiList.WEATHER_MOSTLY_CLOUDS.get();
                result += result + result;
                break;
            case 804:
                result = EmojiList.WEATHER_CLOUD.get();
                result += result + result;
                break;
            default:
                result = "Неизвестный ID";
        }
        return result;
    }

}

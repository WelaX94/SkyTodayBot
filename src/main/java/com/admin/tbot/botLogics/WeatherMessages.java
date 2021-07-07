package com.admin.tbot.botLogics;

import com.admin.tbot.botLogics.convertation.Converting;
import com.admin.tbot.botLogics.convertation.DataToEmoji;
import com.admin.tbot.botLogics.enums.EmojiList;
import com.admin.tbot.models.City;
import com.admin.tbot.models.Coordinates;
import com.admin.tbot.models.Weather;
import com.admin.tbot.repositories.WeatherApiRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Getter
@Setter
public class WeatherMessages {

    @Autowired
    WeatherApiRepository weatherApiRepository;

    final String deg = "\u00B0C";

    public String getCurrentWeather(City city) {
        final Weather.CurrentAndHourlyModel weather = weatherApiRepository.getCurrentWeather(city).getCurrent();
        String result =
                "На улице " + weather.getWeather().get(0).getDescription() + ' ' + DataToEmoji.convertWeather(weather.getWeather().get(0).getId()) + '\n' +
                        EmojiList.THERMOMETER.get() + " Температура " + Math.round(weather.getTemp()) + deg + ", по ощущениям " + Math.round(weather.getFeels_like()) + deg + '\n' +
                        EmojiList.WEATHER_DASH.get() + " Скорость ветра " + weather.getWind_speed() + " м/с, " + Converting.windDegreeToDirectionSimple(weather.getWind_deg()) + ' ' + DataToEmoji.convertWindDegree(weather.getWind_deg()) + '\n' +
                        EmojiList.WEATHER_DROPLET.get() + " Влажность " + weather.getHumidity() + "%\n" +
                        EmojiList.DOUBLE_DOWN.get() + " Давление " + Math.round(weather.getPressure() * 0.75) + " мм. рт. ст.";

        return result;
    }

    public String getDetailedCurrentWeather(City city) {
        final Weather.CurrentAndHourlyModel weather = weatherApiRepository.getCurrentWeather(city).getCurrent();
        String result =
                "На улице " + weather.getWeather().get(0).getDescription() + ' ' + DataToEmoji.convertWeather(weather.getWeather().get(0).getId()) + '\n' +
                        EmojiList.THERMOMETER.get() + " Температура " + String.format("%.1f", weather.getTemp()) + deg + ", по ощущениям " + String.format("%.1f", weather.getFeels_like()) + deg + '\n' +
                        EmojiList.WEATHER_DASH.get() + " Скорость ветра " + weather.getWind_speed() + " м/с, " +
                        Converting.windDegreeToDirectionDetailed(weather.getWind_deg()) + ' ' + DataToEmoji.convertWindDegree(weather.getWind_deg()) + '\n' +
                        EmojiList.WEATHER_DROPLET.get() + " Влажность " + weather.getHumidity() + "%\n" +
                        EmojiList.DOUBLE_DOWN.get() + " Давление " + Math.round(weather.getPressure() * 0.75) + " мм. рт. ст.\n" +
                        EmojiList.BLUE_DIAMOND.get() + " УФ индекс " + weather.getUvi() + '\n' +
                        EmojiList.WEATHER_CLOUD.get() + " Облачность " + weather.getClouds() + "%\n" +
                        EmojiList.EYES.get() + " Дальность видимости " + ((double) weather.getVisibility()) / 1000 + " км\n" +
                        EmojiList.WEATHER_DROPS.get() + " Точка росы " + String.format("%.1f", weather.getDew_point()) + deg;
        return result;
    }

    public String getHourlyForecast(City city, int step) {
        final Weather fullWeather = weatherApiRepository.getHourlyForecastWeather(city);
        final int timezoneOffset = fullWeather.getTimezone_offset();
        final List<Weather.CurrentAndHourlyModel> weather = fullWeather.getHourly();
        String result = "";
        for (int i = 1; i < 25; i += step) {
            result += EmojiList.CALENDAR.get() + ' ' +
                    Converting.unixToStringShortDate(weather.get(i).getDt() + timezoneOffset) + " " +
                    DataToEmoji.convertWeather(weather.get(i).getWeather().get(0).getId()) + " " +
                    Converting.unixToStringTime(weather.get(i).getDt() + timezoneOffset) + " " +
                    DataToEmoji.convertTime(weather.get(i).getDt() + timezoneOffset) + '\n' +
                    EmojiList.RADIO_BUTTON.get() + " Будет " + weather.get(i).getWeather().get(0).getDescription() + '\n' +
                    EmojiList.THERMOMETER.get() + " Температура " + Math.round(weather.get(i).getTemp()) + deg + '\n' +
                    EmojiList.WEATHER_DROPLET.get() + " Влажность " + weather.get(i).getHumidity() + "%\n" +
                    EmojiList.DOUBLE_DOWN.get() + " Давление " + Math.round(weather.get(i).getPressure() * 0.75) + " мм. рт. ст.\n" +
                    EmojiList.WEATHER_RAIN.get() + " Вероятность осадков " + (int) (weather.get(i).getPop() * 100) + "%\n\n";
        }
        return result;
    }

    public String getDailyForecast(City city) {
        final Weather fullWeather = weatherApiRepository.getDailyForecastWeather(city);
        final int timezoneOffset = fullWeather.getTimezone_offset();
        final List<Weather.DailyModel> weather = fullWeather.getDaily();
        weather.remove(0);
        String result = "";
        for (Weather.DailyModel day : weather) {
            result += EmojiList.CALENDAR.get() + " " + Converting.unixToStringDate(day.getDt() + timezoneOffset) + " " + DataToEmoji.convertWeather(day.getWeather().get(0).getId()) + '\n' +
                    EmojiList.RADIO_BUTTON.get() + " Будет " + day.getWeather().get(0).getDescription() + '\n' +
                    EmojiList.THERMOMETER.get() + " Днём " + Math.round(day.getTemp().getDay()) + deg + ", ночью " + Math.round(day.getTemp().getNight()) + deg + '\n' +
                    EmojiList.WEATHER_DROPLET.get() + " Влажность " + day.getHumidity() + "%\n" +
                    EmojiList.DOUBLE_DOWN.get() + " Давление " + Math.round(day.getPressure() * 0.75) + " мм. рт. ст.\n" +
                    EmojiList.WEATHER_RAIN.get() + " Вероятность осадков " + (int) (day.getPop() * 100) + "%\n" +
                    EmojiList.SUNRISE.get() + " Восход в " + Converting.unixToStringTime(day.getSunrise() + timezoneOffset) + ", закат в " + Converting.unixToStringTime(day.getSunset() + timezoneOffset) + "\n\n";
        }
        return result;
    }

    public String getDailyNotification(City city) {
        final Weather fullWeather = weatherApiRepository.getDailyForecastWeather(city);
        final List<Weather.DailyModel> weather = fullWeather.getDaily();
        String result = "";
        result += "Сегодня будет " + weather.get(0).getWeather().get(0).getDescription() + " " + DataToEmoji.convertWeather(weather.get(0).getWeather().get(0).getId()) + '\n' +
                EmojiList.THERMOMETER.get() + " Температура днём " + Math.round(weather.get(0).getTemp().getDay()) + deg + " (по ощущениям " + Math.round(weather.get(0).getFeels_like().getDay()) + deg +
                "), а ночью " + Math.round(weather.get(0).getTemp().getNight()) + deg + " (по ощущениям " + Math.round(weather.get(0).getFeels_like().getNight()) + deg + ")\n" +
                EmojiList.WEATHER_DROPLET.get() + " Влажность " + weather.get(0).getHumidity() + "%\n" +
                EmojiList.DOUBLE_DOWN.get() + " Давление " + Math.round((weather.get(0).getPressure() * 0.75)) + " мм. рт. ст.\n" +
                EmojiList.WEATHER_DASH.get() + " Скорость ветра " + Math.round(weather.get(0).getWind_speed()) + " м/с, " +
                Converting.windDegreeToDirectionSimple(weather.get(0).getWind_deg()) + " " + DataToEmoji.convertWindDegree(weather.get(0).getWind_deg()) + "\n\n" +
                "Завтра будет " + weather.get(1).getWeather().get(0).getDescription() + " " + DataToEmoji.convertWeather(weather.get(1).getWeather().get(0).getId()) + '\n' +
                EmojiList.THERMOMETER.get() + " Температура днём " + Math.round(weather.get(1).getTemp().getDay()) + deg + " (по ощущениям " + Math.round(weather.get(1).getFeels_like().getDay()) + deg +
                "), а ночью " + Math.round(weather.get(1).getTemp().getNight()) + deg + " (по ощущениям " + Math.round(weather.get(1).getFeels_like().getNight()) + deg + ")\n" +
                EmojiList.WEATHER_DROPLET.get() + " Влажность " + weather.get(1).getHumidity() + "%\n" +
                EmojiList.DOUBLE_DOWN.get() + " Давление " + Math.round((weather.get(1).getPressure() * 0.75)) + " мм. рт. ст.\n" +
                EmojiList.WEATHER_DASH.get() + " Скорость ветра " + Math.round(weather.get(1).getWind_speed()) + " м/с, " +
                Converting.windDegreeToDirectionSimple(weather.get(1).getWind_deg()) + " " + DataToEmoji.convertWindDegree(weather.get(1).getWind_deg());

        return result;
    }

    public City checkCity(String city) {
        return weatherApiRepository.checkCity(city);
    }

    public City checkCity(Coordinates coordinates) {
        return weatherApiRepository.checkCity(coordinates.latitude, coordinates.longitude);
    }

}
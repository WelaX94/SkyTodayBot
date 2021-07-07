package com.admin.tbot.repositories;

import com.admin.tbot.models.City;
import com.admin.tbot.models.Weather;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherApiRepository {

    public Weather getCurrentWeather(City city);

    public Weather getHourlyForecastWeather(City city);

    public Weather getDailyForecastWeather(City city);

    public City checkCity(String city);

    public City checkCity(double lat, double lon);

}
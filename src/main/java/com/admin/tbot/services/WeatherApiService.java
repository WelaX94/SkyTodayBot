package com.admin.tbot.services;

import com.admin.tbot.models.City;
import com.admin.tbot.models.Weather;
import com.admin.tbot.repositories.WeatherApiRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Getter
@Setter
public class WeatherApiService implements WeatherApiRepository {

    @Autowired
    RestTemplate restTemplate;

    @Value("${weatherApi.currentWeatherUrl}")
    String currentWeatherUrl;

    @Value("${weatherApi.oneCallWeatherUrl}")
    String oneCallWeatherUrl;

    @Value("appid=" + "${weatherApi.urlParams.token}")
    String urlParamsToken;

    @Value("units=" + "${weatherApi.urlParams.units}")
    String urlParamsUnits;

    @Value("lang=" + "${weatherApi.urlParams.language}")
    String urlParamsLanguage;

    @Override
    public Weather getCurrentWeather(City city) {
        return getWeather(city);
    }

    @Override
    public Weather getHourlyForecastWeather(City city) {
        return getWeather(city);
    }

    @Override
    public Weather getDailyForecastWeather(City city) {
        return getWeather(city);
    }

    private Weather getWeather(City city) {
        final String lat = "lat=" + city.getCoord().getLat();
        final String lon = "lon=" + city.getCoord().getLon();
        final String url = createUrl(oneCallWeatherUrl, lat, lon, urlParamsLanguage, urlParamsUnits, urlParamsToken);
        final ResponseEntity<Weather> responseEntity = restTemplate.exchange
                (url, HttpMethod.GET, null, new ParameterizedTypeReference<Weather>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public City checkCity(String city) {
        city = "q=" + city;
        final String url = createUrl(currentWeatherUrl, city, urlParamsLanguage, urlParamsToken);

        final ResponseEntity<City> responseEntity = restTemplate.exchange
                (url, HttpMethod.GET, null, new ParameterizedTypeReference<City>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public City checkCity(double lat, double lon) {
        final String latitude = "lat=" + lat;
        final String longitude = "lon=" + lon;
        final String url = createUrl(currentWeatherUrl, latitude, longitude, urlParamsLanguage, urlParamsToken);

        final ResponseEntity<City> responseEntity = restTemplate.exchange
                (url, HttpMethod.GET, null, new ParameterizedTypeReference<City>() {
                });
        final City city = responseEntity.getBody();
        if (city.getName().equals("")) city.setName("Неизвестный город");
        if (city.getSys().getCountry() == null) city.getSys().setCountry("waving_white_flag");
        return responseEntity.getBody();
    }

    private String createUrl(String url, String... parameters) {
        for (String parameter : parameters) {
            url += "&" + parameter;
        }
        return url;
    }

}
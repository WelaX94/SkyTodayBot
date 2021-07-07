package com.admin.tbot.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Weather {

    private int timezone_offset;
    private CurrentAndHourlyModel current;
    private List<CurrentAndHourlyModel> hourly;
    private List<DailyModel> daily;

    @Getter
    @Setter
    public static class CurrentAndHourlyModel {
        private long dt;
        private long sunrise;
        private long sunset;
        private double temp;
        private double feels_like;
        private int pressure;
        private int humidity;
        private double dew_point;
        private double uvi;
        private int clouds;
        private int visibility;
        private int wind_speed;
        private int wind_deg;
        private List<WeatherModel> weather;
        private double pop;

        @Getter
        @Setter
        public static class WeatherModel {
            private String description;
            private int id;
        }

    }

    @Getter
    @Setter
    public static class DailyModel {
        private long dt;
        private long sunrise;
        private long sunset;
        private TempModel temp;
        private FeelsLikeModel feels_like;
        private int pressure;
        private int humidity;
        private double wind_speed;
        private int wind_deg;
        private List<WeatherModel> weather;
        private int clouds;
        private double pop;
        private double rain;

        @Getter
        @Setter
        public static class TempModel {
            private double day;
            private double night;
        }

        @Getter
        @Setter
        public static class FeelsLikeModel {
            private double day;
            private double night;
        }

        @Getter
        @Setter
        public static class WeatherModel {
            private String description;
            private int id;
        }

    }

}
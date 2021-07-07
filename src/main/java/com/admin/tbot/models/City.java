package com.admin.tbot.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class City {
    private String name;
    private CoordModel coord;
    private SysModel sys;
    private int timezone;

    @Getter
    @Setter
    public static class CoordModel {
        private double lon;
        private double lat;
    }

    @Getter
    @Setter
    public static class SysModel {
        private String country;
    }

}
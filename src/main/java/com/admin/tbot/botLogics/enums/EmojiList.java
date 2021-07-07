package com.admin.tbot.botLogics.enums;

import com.vdurmont.emoji.EmojiParser;

public enum EmojiList {

    CHECK_YES("white_check_mark"),
    CHECK_NO("x"),
    WEATHER_SUNNY("sunny"),
    WEATHER_SMALL_CLOUDS("white_sun_small_cloud"),
    WEATHER_PARTLY_SUNNY("partly_sunny"),
    WEATHER_MOSTLY_CLOUDS("white_sun_behind_cloud"),
    WEATHER_CLOUD("cloud"),
    WEATHER_RAIN("cloud_rain"),
    WEATHER_THUNDER_WITH_RAIN("thunder_cloud_rain"),
    WEATHER_LIGHTING("cloud_lightning"),
    WEATHER_SNOW("cloud_snow"),
    WEATHER_FOG("fog"),
    WEATHER_DROPLET("droplet"),
    WEATHER_DROPS("sweat_drops"),
    WEATHER_DASH("dash"),
    WEATHER_TORNADO("cloud_tornado"),
    CALENDAR("spiral_calendar_pad"),
    CLOCK_1("clock1"),
    CLOCK_2("clock2"),
    CLOCK_3("clock3"),
    CLOCK_4("clock4"),
    CLOCK_5("clock5"),
    CLOCK_6("clock6"),
    CLOCK_7("clock7"),
    CLOCK_8("clock8"),
    CLOCK_9("clock9"),
    CLOCK_10("clock10"),
    CLOCK_11("clock11"),
    CLOCK_12("clock12"),
    MAILBOX_EMPTY("mailbox_closed"),
    MAILBOX_FULL("mailbox"),
    THERMOMETER("thermometer"),
    DOUBLE_DOWN("arrow_double_down"),
    BLUE_DIAMOND("small_blue_diamond"),
    EYES("eyes"),
    RADIO_BUTTON("radio_button"),
    ARROW_UP("arrow_up"),
    ARROW_DOWN("arrow_down"),
    ARROW_LEFT("arrow_left"),
    ARROW_RIGHT("arrow_right"),
    ARROW_UPPER_RIGHT("arrow_upper_right"),
    ARROW_UPPER_LEFT("arrow_upper_left"),
    ARROW_LOWER_RIGHT("arrow_lower_right"),
    ARROW_LOWER_LEFT("arrow_lower_left"),
    SUNRISE("sunrise");

    private String value;

    public String get() {
        return EmojiParser.parseToUnicode(value);
    }

    EmojiList(String value) {
        this.value = ":" + value + ":";
    }

}
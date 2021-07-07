package com.admin.tbot.models;

import com.admin.tbot.botLogics.enums.BotStatus;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "userlist")
public class User {

    @Column (name = "chatId")
    @Id
    private long chatId;
    @Column (name = "city")
    private String city;
    @Column (name = "country")
    private String country;
    @Column (name = "lat")
    private double lat;
    @Column (name = "lon")
    private double lon;
    @Column (name = "utc")
    private String utc = "UTC";
    @Column (name = "notificationTime")
    private LocalDateTime notificationTime = LocalDateTime.now();
    @Column (name = "dailyMailing")
    private boolean dailyMailing = false;
    @Column (name = "botStatus")
    @Enumerated(EnumType.STRING)
    private BotStatus botStatus = BotStatus.START;
    @Column (name = "registrationComplete")
    private boolean registrationComplete = false;

    public User(long chatId) {
        this.chatId = chatId;
    }

    public User() {
    }

    public City getCityInfo() {
        final City cityInfo = new City();
        cityInfo.setName(this.city);
        final City.CoordModel coord = new City.CoordModel();
        coord.setLat(this.lat);
        coord.setLon(this.lon);
        final City.SysModel sys = new City.SysModel();
        sys.setCountry(this.country);
        cityInfo.setCoord(coord);
        cityInfo.setSys(sys);
        return cityInfo;
    }

    public void setCityInfo(City cityInfo) {
        this.city = cityInfo.getName();
        this.lat = cityInfo.getCoord().getLat();
        this.lon = cityInfo.getCoord().getLon();
        this.country = ':' + cityInfo.getSys().getCountry().toLowerCase() + ':';
        final int timezone = cityInfo.getTimezone() / 3600;
        String utc = "UTC";
        if (timezone > 0) {
            utc += "+" + timezone;
        } else if (timezone < 0) {
            utc += timezone;
        }
        this.utc = utc;
    }

    public void setDefaultNotificationTime() {
        final LocalDateTime sysTime = LocalDateTime.now(ZoneId.of(this.utc));
        LocalDateTime newNotificationTime =
                LocalDateTime.of(sysTime.getYear(), sysTime.getMonth(), sysTime.getDayOfMonth(), 10, 0);
        if (newNotificationTime.isBefore(sysTime)) newNotificationTime = newNotificationTime.plusDays(1);
        this.notificationTime = newNotificationTime;
    }

    public void setNewNotificationTime(LocalTime notificationTime) {
        final LocalDateTime sysTime = LocalDateTime.now(ZoneId.of(this.utc));
        LocalDateTime newNotificationTime = LocalDateTime.of
                (sysTime.getYear(), sysTime.getMonth(), sysTime.getDayOfMonth(), notificationTime.getHour(), notificationTime.getMinute());
        if (newNotificationTime.isBefore(sysTime)) newNotificationTime = newNotificationTime.plusDays(1);
        this.notificationTime = newNotificationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return chatId == user.chatId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId);
    }
}
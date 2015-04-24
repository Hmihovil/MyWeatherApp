package com.example.prajnashetty.myweatherapp2.javabeans;

/**
 * Created by prajnashetty on 4/24/15.
 *
 * This class stores timezone information like timezone id, sunrise, sunset times ,etc.
 */
public class LocalTimeZone {

    /* local sunrise time */
    private String sunrise;

    /* country code */
    private String countryCode;

    /* local sunset time */
    private String sunset;

    /* local timezone id */
    private String timezoneId;

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(String timezoneId) {
        this.timezoneId = timezoneId;
    }

    @Override
    public String toString() {
        return "LocalTimeZone{" +
                "sunrise='" + sunrise + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", sunset='" + sunset + '\'' +
                ", timezoneId='" + timezoneId + '\'' +
                '}';
    }
}

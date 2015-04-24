package com.example.prajnashetty.myweatherapp2.javabeans;

/**
 * Created by prajnashetty on 4/22/15.
 *
 *  This class represents the WeatherObservation within the
 *  WeatherInformation object returned by the GeoNames Webservice.
 */
public class WeatherObservation {

    private double lng;
    private double lat;
    private String observation;
    private String ICAO;
    private String clouds;
    private String cloudsCode;
    private String datetime;
    private double temperature;
    private double humidity;
    private String stationName;
    private String weatherCondition;


    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getICAO() {
        return ICAO;
    }

    public void setICAO(String ICAO) {
        this.ICAO = ICAO;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getCloudsCode() {
        return cloudsCode;
    }

    public void setCloudsCode(String cloudsCode) {
        this.cloudsCode = cloudsCode;
    }

    public String getDateTime() {
        return datetime;
    }

    public void setDateTime(String dateTime) {
        this.datetime = dateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "WeatherObservation{" +
                "lng=" + lng +
                ", lat=" + lat +
                ", observation='" + observation + '\'' +
                ", ICAO='" + ICAO + '\'' +
                ", clouds='" + clouds + '\'' +
                ", cloudsCode='" + cloudsCode + '\'' +
                ", datetime='" + datetime + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", stationName='" + stationName + '\'' +
                ", weatherCondition='" + weatherCondition + '\'' +
                '}';
    }
}

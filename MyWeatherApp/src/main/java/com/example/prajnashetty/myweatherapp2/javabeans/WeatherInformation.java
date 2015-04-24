package com.example.prajnashetty.myweatherapp2.javabeans;

/**
 * Created by prajnashetty on 4/23/15.
 *
 * This class represents the WeatherInformation object returned by the GeoNames Webservice.
 */
public class WeatherInformation {

    /* object containing weather information */
    private WeatherObservation weatherObservation;

    public WeatherObservation getWeatherObservation() {
        return weatherObservation;
    }

    public void setWeatherObservation(WeatherObservation weatherObservation) {
        this.weatherObservation = weatherObservation;
    }

    @Override
    public String toString() {
        return "WeatherInformation{" +
                "weatherObservation=" + weatherObservation +
                '}';
    }
}

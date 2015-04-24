package com.example.prajnashetty.myweatherapp2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prajnashetty on 4/23/15.
 */
public class WeatherConditions {

    static HashMap<String, String> conditionMap = new HashMap<String, String>();

    static {
        conditionMap.put("sunny", "ClearDay");
        conditionMap.put("clear", "ClearDay");
        conditionMap.put("clear sky", "ClearDay");

        conditionMap.put("sunny intervals", "MostlySunny");
        conditionMap.put("few clouds", "MostlySunny");
        conditionMap.put("scattered clouds", "MostlySunny");
        conditionMap.put("nil significant cloud", "MostlySunny");
        conditionMap.put("clouds and visibility OK", "MostlySunny");

        conditionMap.put("partly cloudy", "MostlyCloudy");
        conditionMap.put("broken clouds", "MostlyCloudy");


        conditionMap.put("white cloud", "Overcast");
        conditionMap.put("overcast", "Overcast");
        conditionMap.put("grey cloud", "Overcast");
        conditionMap.put("cloudy", "Overcast");

        conditionMap.put("drizzle", "LightRain");
        conditionMap.put("light drizzle", "LightRain");
        conditionMap.put("in vicinity:  showers ", "LightRain");
        conditionMap.put("light shower", "LightRain");
        conditionMap.put("light rain shower", "LightRain");
        conditionMap.put("light showers", "LightRain");
        conditionMap.put("light rain", "LightRain");


        conditionMap.put("heavy rain", "Rain");
        conditionMap.put("heavy showers", "Rain");
        conditionMap.put("heavy shower", "Rain");
        conditionMap.put("heavy rain shower", "Rain");

        conditionMap.put("misty", "Mist");
        conditionMap.put("mist", "Mist");
        conditionMap.put("fog", "Mist");
        conditionMap.put("foggy", "Mist");
        conditionMap.put("dense fog", "Mist");
        conditionMap.put("Thick Fog", "Mist");
        conditionMap.put("hazy", "Mist");
        conditionMap.put("haze", "Mist");

        conditionMap.put("NotAvailable", "NotAvailable");
        conditionMap.put("n/a", "NotAvailable");
        conditionMap.put("N/A", "NotAvailable");
        conditionMap.put("na", "NotAvailable");

        conditionMap.put("tropical storm", "Thunderstorm");
        conditionMap.put("thunderstorm", "Thunderstorm");
        conditionMap.put("thundery shower", "Thunderstorm");
        conditionMap.put("thunder storm", "Thunderstorm");

        conditionMap.put("light snow", "LightSnow");
        conditionMap.put("light snow shower", "LightSnow");
        conditionMap.put("cloudy with light snow", "LightSnow");
        conditionMap.put("light snow showers", "LightSnow");

        conditionMap.put("heavy snow", "Snow");
        conditionMap.put("heavy snow shower", "Snow");
        conditionMap.put("heavy snow showers", "Snow");
        conditionMap.put("cloudy with heavy snow", "Snow");

        conditionMap.put("hail shower", "Hail");
        conditionMap.put("hail showers", "Hail");
        conditionMap.put("cloudy with hail", "Hail");
        conditionMap.put("hail", "Hail");

        conditionMap.put("cloudy with sleet", "RainSnow");
        conditionMap.put("sleet shower", "RainSnow");
        conditionMap.put("sleet showers", "RainSnow");
        conditionMap.put("sleet", "RainSnow");

    }
}

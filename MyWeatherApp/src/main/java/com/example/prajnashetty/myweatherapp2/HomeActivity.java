package com.example.prajnashetty.myweatherapp2;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prajnashetty.myweatherapp2.javabeans.LocalTimeZone;
import com.example.prajnashetty.myweatherapp2.javabeans.WeatherInformation;
import com.example.prajnashetty.myweatherapp2.javabeans.WeatherObservation;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class HomeActivity extends FragmentActivity {

    //input widgets
    private Button mButtonGet;
    private EditText mEditICAOCode;

    //output widgets
    private ImageView weatherImg;
    private TextView mTextResult;
    private TextView mTextTempValue;
    private TextView mTextHumidityValue;
    private TextView mTextCloudsValue;
    private TextView mTextWeatherCondition;
    private TextView mTextDateTime;
    private TextView mTextSunrise;
    private TextView mTextSunset;

    //objects carrying weather and timezone info
    private WeatherInformation weatherInfo;
    private LocalTimeZone localTimeZone;

    //to store previous search data for persistence
    private SharedPreference sharedPreference;

    //context of current activity
    Activity context = this;

    //constant URLS
    private static final String baseICAOURL = "http://api.geonames.org/weatherIcaoJSON?";
    private static final String baseTimeZoneURL = "http://api.geonames.org/timezoneJSON?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreference = new SharedPreference();

        // Replace the contents of the container with the new fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.my_placeholder, new PlaceholderFragment());
        ft.commit();

        //check if data is saved
        if (sharedPreference.getValue(context) != null) {

            //call webservice client in AsyncTask using last searched code
            (new WeatherServiceClient()).execute(sharedPreference.getValue(context));
        }

        //add Listener to 'get weather information' button
        addListenerOnButton(sharedPreference);
    }

   public void addListenerOnButton(final SharedPreference sharedPreference) {

        mButtonGet = (Button) findViewById(R.id.get_weather_button);
        mEditICAOCode = (EditText)findViewById(R.id.icao_code);

        mButtonGet.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                String icaoCode = mEditICAOCode.getText().toString();
                mEditICAOCode.setText(""); //clear text field

                //Call AsyncTask - runs in the background thread, not main UI thread
                //Record time to run webservice & display results
                long start = System.currentTimeMillis();
                (new WeatherServiceClient()).execute(icaoCode);
                long end = System.currentTimeMillis();
                long result = (end-start);
                Log.d("HomeActivity", "Time for Webservice & Display = " + result);

                // Hides the soft keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEditICAOCode.getWindowToken(), 0);

                // Save the last searched ICAO code in SharedPreference
                sharedPreference.save(context, icaoCode);

            }

        });

    }


    //fragment showing the results
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }
    }

    //AysnTask that calls webservice
    private class WeatherServiceClient extends AsyncTask<String, Void, String> {

        //calls webservice in background thread using HttpGet
        @Override
        protected String doInBackground(String... airportCode)
        {
            Log.d("WeatherServiceClient", "ICAOCode = " + airportCode[0]);
            String line = null;
            StringBuilder builder = new StringBuilder();
            WeatherObservation weatherObject = null;

            if ((airportCode.length == 1) && !airportCode[0].contains(" ")) {

                String finalICAOURL = baseICAOURL + "ICAO=" + airportCode[0].toUpperCase() + "&username=prajnashetty";
                Log.d("WeatherServiceClient", "finalRequestURL = " + finalICAOURL);
                HttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(finalICAOURL);

                try {
                    // Send GET request
                    Log.i("WeatherServiceClient", "Sending GET request");
                    HttpResponse response = client.execute(httpGet);
                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    Log.i("WeatherServiceClient", "StatusCode = " + statusCode);

                    if (statusCode == 200) {
                        HttpEntity entity = response.getEntity();
                        InputStream content = entity.getContent();
                        BufferedReader br = new BufferedReader(new InputStreamReader(content));
                        while ((line = br.readLine()) != null) {
                            builder.append(line);
                        }
                    } else {
                        Log.e("WeatherServiceClient", "Webservice failed");
                    }

                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();
                weatherInfo = gson.fromJson(builder.toString(), WeatherInformation.class);
                Log.d("WeatherServiceClient", "WeatherInformation = " + weatherInfo.toString());

                if (weatherInfo != null && weatherInfo.getWeatherObservation() != null) {

                    if ((weatherInfo.getWeatherObservation().getLng() != 0.0 &&
                            weatherInfo.getWeatherObservation().getLat() != 0.0)) {
                        //getTimeZone
                        String timezoneURL = baseTimeZoneURL + "lat=" + weatherInfo.getWeatherObservation().getLat() +
                                "&lng=" + weatherInfo.getWeatherObservation().getLng() +
                                "&username=prajnashetty";
                        httpGet = new HttpGet(timezoneURL);
                        builder = new StringBuilder();

                        try {
                            // Send GET request
                            Log.i("WeatherServiceClient", "Sending GET request");
                            HttpResponse response = client.execute(httpGet);
                            StatusLine statusLine = response.getStatusLine();
                            int statusCode = statusLine.getStatusCode();
                            Log.i("WeatherServiceClient", "StatusCode = " + statusCode);

                            if (statusCode == 200) {
                                HttpEntity entity = response.getEntity();
                                InputStream content = entity.getContent();
                                BufferedReader br = new BufferedReader(new InputStreamReader(content));
                                while ((line = br.readLine()) != null) {
                                    builder.append(line);
                                }
                            } else {
                                Log.e("WeatherServiceClient", "Webservice failed");
                            }

                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        localTimeZone = gson.fromJson(builder.toString(), LocalTimeZone.class);
                        Log.d("WeatherServiceClient", "WeatherInformation = " + localTimeZone.toString());
                    }

                }
            }

            return "";
        }


        @Override
        protected void onPostExecute(String result) {

            DateFormat indfm = null, outdfm = null;
            Date present = null;
            String localDateTime = null;

            mTextResult  = (TextView)findViewById(R.id.resultOut);
            mTextTempValue = (TextView)findViewById(R.id.textTempValue);
            mTextHumidityValue = (TextView)findViewById(R.id.textHumidityValue);
            mTextCloudsValue = (TextView)findViewById(R.id.textCloudsValue);
            weatherImg = (ImageView) findViewById(R.id.image);
            mTextWeatherCondition = (TextView)findViewById(R.id.textConditionValue);
            mTextDateTime = (TextView)findViewById(R.id.textDateTimeValue);
            mTextSunrise = (TextView)findViewById(R.id.textSunriseValue);
            mTextSunset = (TextView)findViewById(R.id.textSunsetValue);


            if (weatherInfo != null && weatherInfo.getWeatherObservation() != null) {

                //converting UTC timezone timestamp to local timezone timestamp of airport
                WeatherObservation weatherObs = weatherInfo.getWeatherObservation();
                if (weatherObs.getDateTime() != null ) {
                    indfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    indfm.setTimeZone(TimeZone.getTimeZone("UTC"));
                    try {
                        present= indfm.parse(weatherObs.getDateTime());
                        outdfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        outdfm.setTimeZone(TimeZone.getTimeZone(localTimeZone.getTimezoneId()));
                        localDateTime = outdfm.format(present);
                    }catch(ParseException e)
                    {
                        e.printStackTrace();
                    }

                }

                //setting output widgets with data from webservice
                mTextResult.setText(weatherInfo.getWeatherObservation().getStationName());
                setWeatherImage(localDateTime);

                //converting temperature from celsius to fahrenheit
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                nf.setGroupingUsed(false);
                mTextTempValue.setText(String.valueOf
                        ((nf.format(weatherInfo.getWeatherObservation().getTemperature()*1.800 +32)))+ " F");

                mTextCloudsValue.setText(weatherInfo.getWeatherObservation().getClouds());
                mTextHumidityValue.setText(String.valueOf(weatherInfo.getWeatherObservation().getHumidity()) + " %");
                mTextWeatherCondition.setText(String.valueOf(weatherInfo.getWeatherObservation().getWeatherCondition()));
                mTextDateTime.setText(localDateTime);
                if (localTimeZone.getSunrise() != null && !localTimeZone.getSunrise().equals(""))
                    mTextSunrise.setText(localTimeZone.getSunrise().split(" ")[1]);
                if (localTimeZone.getSunset() != null && !localTimeZone.getSunset().equals(""))
                    mTextSunset.setText(localTimeZone.getSunset().split(" ")[1]);
            }
            else {
                // setting to 'not available' if data not available or entered ICAO code is wrong
                mTextResult.setText("No observation found at " + mEditICAOCode.getText().toString().toUpperCase());
                weatherImg.setImageResource(R.drawable.imageno);
                mTextTempValue.setText(String.valueOf("Not Available"));
                mTextCloudsValue.setText(String.valueOf("Not Available"));
                mTextHumidityValue.setText(String.valueOf("Not Available"));
                mTextWeatherCondition.setText(String.valueOf("Not Available"));
                mTextDateTime.setText(String.valueOf("Not Available"));
                mTextSunrise.setText("Not Available");
                mTextSunset.setText("Not Available");
            }

        }

        //this method decides which image to display based on weather conditions and time of day
        private void setWeatherImage(String localDateTime)
        {
            Log.d("WeatherServiceClient",  "Setting Weather Image");
            String criteria = null;
            String imageType = null;

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone(localTimeZone.getTimezoneId()));
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            sdf.setTimeZone(TimeZone.getTimeZone(localTimeZone.getTimezoneId()));

            boolean isDay = false;

            //setting criteria for image based on information from clouds and weather information field
            WeatherObservation weatherObs = weatherInfo.getWeatherObservation();
            if (!weatherObs.getWeatherCondition().equalsIgnoreCase("n/a"))
                criteria = weatherObs.getWeatherCondition();
            else if (weatherObs.getWeatherCondition().equalsIgnoreCase("n/a"))
                criteria = weatherObs.getClouds();
            else if (weatherObs.getWeatherCondition().equalsIgnoreCase("n/a") &&
                    weatherObs.getWeatherCondition().equalsIgnoreCase("n/a"))
                criteria = "NotAvailable";

            //determine if day or night
            if (localDateTime != null)
            {
                try {
                    Timestamp localTimeTS = new Timestamp(df.parse(localDateTime).getTime());
                    Timestamp sunriseTS = new Timestamp(sdf.parse(localTimeZone.getSunrise()).getTime());
                    Timestamp sunsetTS = new Timestamp(sdf.parse(localTimeZone.getSunset()).getTime());

                    if (localTimeTS.after(sunriseTS) && localTimeTS.before(sunsetTS))
                        isDay = true;
                }
                catch(ParseException e)
                {
                    e.printStackTrace();
                }

            }

            //determine weather condition
            if (WeatherConditions.conditionMap.containsKey(criteria))
            {
                imageType = WeatherConditions.conditionMap.get(criteria);
                Log.d("WeatherServiceClient", "imageType = " + imageType);
                if (imageType.equalsIgnoreCase("Thunderstorm"))
                    weatherImg.setImageResource(R.drawable.thunderstorms);
                else if (imageType.equalsIgnoreCase("Mist"))
                    weatherImg.setImageResource(R.drawable.fog);
                else if (imageType.equalsIgnoreCase("Snow"))
                    weatherImg.setImageResource(R.drawable.snow);
                else if (imageType.equalsIgnoreCase("Hail"))
                    weatherImg.setImageResource(R.drawable.hail);
                else if (imageType.equalsIgnoreCase("RainSnow"))
                    weatherImg.setImageResource(R.drawable.rain_snow);
                else {
                    if (isDay) {
                        if (imageType.equalsIgnoreCase("MostlyCloudy"))
                            weatherImg.setImageResource(R.drawable.day_mostly_cloudy);
                        else if (imageType.equalsIgnoreCase("MostlySunny"))
                            weatherImg.setImageResource(R.drawable.day_mostly_sunny);
                        else if (imageType.equalsIgnoreCase("Overcast"))
                            weatherImg.setImageResource(R.drawable.cloudy);
                        else if (imageType.equalsIgnoreCase("ClearDay"))
                            weatherImg.setImageResource(R.drawable.day_sunny);
                        else if (imageType.equalsIgnoreCase("LightRain"))
                            weatherImg.setImageResource(R.drawable.day_light_rain);
                        else if (imageType.equalsIgnoreCase("LightSnow"))
                            weatherImg.setImageResource(R.drawable.day_light_snow);
                        else if (imageType.equalsIgnoreCase("NotAvailable"))
                            weatherImg.setImageResource(R.drawable.imageno);
                    }
                    else
                    {
                        if (imageType.equalsIgnoreCase("MostlyCloudy"))
                            weatherImg.setImageResource(R.drawable.night_cloudy);
                        else if (imageType.equalsIgnoreCase("MostlySunny"))
                            weatherImg.setImageResource(R.drawable.night_cloudy);
                        else if (imageType.equalsIgnoreCase("Overcast"))
                            weatherImg.setImageResource(R.drawable.night_cloudy);
                        else if (imageType.equalsIgnoreCase("ClearDay"))
                            weatherImg.setImageResource(R.drawable.night_clear);
                        else if (imageType.equalsIgnoreCase("LightRain"))
                            weatherImg.setImageResource(R.drawable.night_light_rain);
                        else if (imageType.equalsIgnoreCase("LightSnow"))
                            weatherImg.setImageResource(R.drawable.night_light_snow);
                        else if (imageType.equalsIgnoreCase("NotAvailable"))
                            weatherImg.setImageResource(R.drawable.imageno);
                    }
                }
            }
            else {
                weatherImg.setImageResource(R.drawable.imageno);
            }

        }

    }
}

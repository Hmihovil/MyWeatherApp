PROJECT NAME : MyWeatherApp
AUTHOR: Prajna Shetty
EMAIL: prajna.shetty@email.ucr.edu

CONTENTS OF THIS FILE
---------------------
 * Introduction
 * Requirements
 * How to run
 * Description of source files
 * Further improvements 
 * IDE used

INTRODUCTION
------------
This MyWeatherApp will display the current weather at an airport given the ICAO code of the airport. For example, when the ICAO code of San Francisco Airport, which is KSFO is entered, the app will display the current weather at the San Francisco Airport.  This application is implemented using Android and will run on only Android based devices.

This app uses two web services from GeoNames.org.(http://www.geonames.org/about.html). They are -

1. weatherIcaoJSON

Webservice Type : REST 
Url : api.geonames.org/weatherIcaoJSON?
Parameters : 
ICAO : International Civil Aviation Organization (ICAO) code 
Callback : name of javascript function (optional parameter) 
Result : returns the weather station and the most recent weather observation for the ICAO code
Example: http://api.geonames.org/weatherIcaoJSON?ICAO=KSFO&username=prajnashetty

2. timezoneJSON
(I used this additional service to convert UTC time obtained from the above web service to the local time zone of the airport. For this i needed the timezone id, which this web service gives.)

Webservice Type : REST 
Url : api.geonames.org/timezoneJSON?
Parameters : lat,lng, radius (buffer in km for closest timezone in coastal areas),lang (for countryName), date (date for sunrise/sunset);
Result : the timezone at the lat/lng with gmt offset (1. January) and dst offset (1. July) 
Example: http://api.geonames.org/timezoneJSON?lat=47.01&lng=10.2&username=demo 

Some salient features of this app 
1) This app will display the weather information at an airport such as temperature, humidity, clouds, sunrise time, sunset time, local time at airport (last weather recorded time), etc.
2) This app will display an image of the weather condition at the airport. This image changes depending on the weather condition at the airport and also time of day.
3) This app when opened will display the weather information of the last airport searched before closing of the app. This last searched information is displayed when opening the app at all times, except when an app is newly installed or the applications saved data is cleared form the Settings.  

REQUIREMENTS
------------
This module requires the following additional items to run:
* Android emulator or device
* Android sdk & adb installed on your system
 
HOW TO RUN
-----------
This project requires running the following class files with main methods. Follow the below steps to run the project.

1) Copy the MyWeatherApp-release.apk file on your local system.

2) Open a terminal and get into the sdk/platform-tools/ folder. Here type the below command to install on the USB connected androd device.

./adb install -d <path_to_apk_file>

To install on an emulator, type the below command.

./adb -s <emulator_name> install <path_to_apk_file>

You should see a message like shown below, if installation is successful.

6840 KB/s (1273805 bytes in 0.181s)
	pkg: /data/local/tmp/MyWeatherApp-release.apk
Success 

4) Open the app on you device or emulator.

DESCRIPTION OF SOURCE FILES
-----------------------
This project contains the following packages

1) com.example.prajnashetty.myweatherapp2.javabeans - This folder contains the class representation of the Java objects used by this project. They are:-

a. LocalTimeZone - contains timezone ids, sunrise, sunset times and country code. Timezone id is used to convert UTC time to local timezone of airport. 
b. WeatherInformation - contains the WeatherObservation object.
c. WeatherObservation - contains information about weather conditions at the airport, ICAO code of airport, current UTC time, latitude and longitude of airport, station name, etc.

2) com.example.prajnashetty.myweatherapp2  - This folder contains the following files.

a. HomeActivity - The main activity screen that extends FragmentActivity. This class fetches input data from text field, calls a background thread to call the web services, and then displays weather information on the screen.
b. SharedPreference - This class contains methods to save and retrieve data from the default getDefaultSharedPreferences of the application.
c. Weather Conditions - This class contains a map of weather condition mapped to a weather criteria , which is then used to display images.

3)res/layout

a. activity_home.xml - XML rendition of home activity
b. fragment_home.xml - XML rendition of fragment embedded in home activity

4) res/drawable - contains weather images (PNG files) used by the app

5) res/values - contains dimensions and strings used by the app.


FURTHER IMPROVEMENTS
-----------------------
Further improvements that can be done given time, are as follows - 

1)Instead of entering ICAO code , if the user enters name of airport or city of airport, the weather condition should be displayed. If a city has multiple airports , there should be a provision to choose an airport. This would require mapping of airport name to ICAO code to be available.

2)Display weather forecasts. This would also require this data to be available, preferable through a web service.

3)Pictures of the airport can be displayed using the Flickr web services.

4)Display information about hotels, car rentals, restaurants, etc. at or in the vicinity of the airport.

5)Display news, emergency alerts, etc. of the location of the airport. 

6)Make the UI more fancy.
 
IDE USED
----------
I have used Android Studio 1.1.0 to write and build the app. 
 

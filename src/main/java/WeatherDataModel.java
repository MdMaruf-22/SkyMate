import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONObject;
import org.json.JSONArray;
public class WeatherDataModel {
    private String city;
    private String language;
    private int temperatureCelsius;
    private double tempMin;
    private double tempMax;
    private int humidity;
    private int cloudiness;
    private int windSpeed;
    private String description;
    private String countryName;
    private long sunrise;
    private String sunriseTime;
    private long sunset;
    private String sunsetTime;
    private List<String> favoriteCities = new ArrayList<>();

    public void setCity(String city) {
        this.city = city;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public String getLanguage() {
        return language;
    }

    // Fetches the current weather data from the API and sets the corresponding properties.
    public void fetchCurrentWeatherData(String apiKey) throws Exception {
        String apiUrlCurrent = "http://api.openweathermap.org/data/2.5/weather?q=";
        URL urlCurrent = new URL(apiUrlCurrent + city + "&appid=" + apiKey + "&units=metric");
        HttpURLConnection connectionCurrent = (HttpURLConnection) urlCurrent.openConnection();
        connectionCurrent.setRequestMethod("GET");

        BufferedReader readerCurrent = new BufferedReader(new InputStreamReader(connectionCurrent.getInputStream()));
        StringBuilder responseCurrent = new StringBuilder();
        String lineCurrent;

        while ((lineCurrent = readerCurrent.readLine()) != null) {
            responseCurrent.append(lineCurrent);
        }

        readerCurrent.close();
        connectionCurrent.disconnect();

        JSONObject weatherData = new JSONObject(responseCurrent.toString());
        JSONObject mainCurrent = weatherData.getJSONObject("main");
        JSONObject clouds = weatherData.getJSONObject("clouds");
        JSONObject wind = weatherData.getJSONObject("wind");
        JSONObject weather = weatherData.getJSONArray("weather").getJSONObject(0);
        JSONObject sys = weatherData.getJSONObject("sys");

        temperatureCelsius = mainCurrent.getInt("temp");
        tempMin = mainCurrent.getDouble("temp_min");
        tempMax = mainCurrent.getDouble("temp_max");
        humidity = mainCurrent.getInt("humidity");
        cloudiness = clouds.getInt("all");
        windSpeed = wind.getInt("speed");
        description = weather.getString("description");
        countryName = sys.getString("country");

        sunrise = sys.getLong("sunrise") * 1000;
        sunset = sys.getLong("sunset") * 1000;
        sunriseTime = convertTimestampToTime(sunrise);
        sunsetTime = convertTimestampToTime(sunset);
    }

    public JSONArray fetchForecastData(String apiKey) throws Exception {
        String apiUrlForecast = "http://api.openweathermap.org/data/2.5/forecast?q=";
        URL urlForecast = new URL(apiUrlForecast + city + "&appid=" + apiKey + "&units=metric&lang=" + language);
        HttpURLConnection connectionForecast = (HttpURLConnection) urlForecast.openConnection();
        connectionForecast.setRequestMethod("GET");

        BufferedReader readerForecast = new BufferedReader(new InputStreamReader(connectionForecast.getInputStream()));
        StringBuilder responseForecast = new StringBuilder();
        String lineForecast;
        while ((lineForecast = readerForecast.readLine()) != null) {
            responseForecast.append(lineForecast);
        }

        readerForecast.close();
        connectionForecast.disconnect();

        JSONObject forecastData = new JSONObject(responseForecast.toString());

        JSONArray originalList = forecastData.getJSONArray("list");
        JSONArray filteredList = new JSONArray();

        long previousDate = 0;
        for (int i = 0; i < originalList.length(); i++) {
            JSONObject item = originalList.getJSONObject(i);
            long timestamp = item.getLong("dt");
            String date = convertTimestampToDate(timestamp);
            int hour = convertTimestampToHour(timestamp);

            if (timestamp - previousDate >= 24 * 60 * 60 && hour == 9) {
                filteredList.put(item);
                previousDate = timestamp;
            }
        }

        return filteredList;
    }

    private String convertTimestampToDate(long timestamp) {
        Date date = new Date(timestamp * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    private int convertTimestampToHour(long timestamp) {
        Date date = new Date(timestamp * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        return Integer.parseInt(sdf.format(date));
    }

    private String convertTimestampToTime(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        return sdf.format(date);
    }

    public void addCityToFavorites(String city) {
        if (!favoriteCities.contains(city)) {
            favoriteCities.add(city);
        }
    }

    public List<String> getFavoriteCities() {
        if(!favoriteCities.isEmpty()) loadFavoriteCities();
        return favoriteCities;
    }

    private static final String FAVORITES_FILE = "src/main/resources/favorites.json";

    // Loads favorite cities from the "favorites.json" file.
    public void  loadFavoriteCities(){
        try {
            // Read the JSON file
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/favorites.json")));

            // Parse the JSON array
            JSONArray jsonArray = new JSONArray(jsonString);

            favoriteCities.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                String element = jsonArray.getString(i);

                favoriteCities.add(element);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Saves favorite cities to the "favorites.json" file.
    public void saveFavoriteCities() {
        try {
            JSONArray jsonArray = new JSONArray(favoriteCities);
            Files.write(Paths.get(FAVORITES_FILE), jsonArray.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void removeCityFromFavorites(String city) {
        favoriteCities.remove(city);
    }
    public int getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public int getHumidity() {
            return humidity;
        }

        public int getCloudiness () {
            return cloudiness;
        }

        public int getWindSpeed () {
            return windSpeed;
        }

        public String getDescription () {
            return description;
        }

        public String getCountryName () {
            return countryName;
        }

        public long getSunrise () {
            return sunrise;
        }

        public long getSunset () {
            return sunset;
        }

    public String getSunriseTime() {
        return sunriseTime;
    }
    public String getSunsetTime() {
        return sunsetTime;
    }
    public boolean isItFav(String s){
        if(favoriteCities.contains(s)) return true;
        return false;
    }
}



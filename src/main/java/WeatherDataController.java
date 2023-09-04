
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import java.awt.event.ActionListener;
public class WeatherDataController {
    private WeatherDataModel model;
    private  WeatherDataView view;
    public WeatherDataController(WeatherDataModel model,WeatherDataView view){
        this.model=model;
        this.view=view;
        final DefaultListModel<String>[] favoriteCitiesListModel = new DefaultListModel[]{new DefaultListModel<>()};
        for (String city : model.getFavoriteCities()) {
            favoriteCitiesListModel[0].addElement(city);
        }
        try {
            model.setCity("Sierre");
            model.setLanguage("en");
            model.fetchCurrentWeatherData("dee99d2e1ba9baa09ddbe17c094d8e49");
            String city= model.getCity();
            String country=model.getCountryName();
            int temp=model.getTemperatureCelsius();
            view.setCityName(city+","+country);
            String s= Integer.toString(temp);
            String format= "<html>" + s + "<sup><font size='+2'>°c</font></sup></html>";
            view.setTempText(format);
            view.setAdditionalLabel(model.getDescription());
            view.updateWeatherIcon(model.getDescription());
            int hum= model.getHumidity();
            s= Integer.toString(hum);
            view.setLabel1(s);
            int wind=model.getWindSpeed();
            s= Integer.toString(wind);
            view.setLabel2(s);
            int cloud=model.getCloudiness();
            s= Integer.toString(cloud);
            view.setLabel3(s);
            s = model.getTempMax() + "°c";
            s = s+"\n"+ model.getTempMin()+"°c";
            view.setLabel4(s);
            view.setLabel4(s);
            view.setLabel5(model.getSunriseTime());
            view.setLabel6(model.getSunsetTime());

            JSONArray forecastData = model.fetchForecastData("dee99d2e1ba9baa09ddbe17c094d8e49");
            StringBuilder forecastTextBuilder = new StringBuilder();
            String previousDay = ""; // Store the previous day for comparison
            forecastTextBuilder.append("\t\t4 DAY WEATHER FORECAST\n\n");
            for (int i = 0; i < forecastData.length(); i++) {
                JSONObject forecastItem = forecastData.getJSONObject(i);
                long timestamp = forecastItem.getLong("dt") * 1000;
                Date date = new Date(timestamp);
                double tempMin = forecastItem.getJSONObject("main").getDouble("temp_min");
                double tempMax = forecastItem.getJSONObject("main").getDouble("temp_max");
                String description = forecastItem.getJSONArray("weather").getJSONObject(0).getString("description");

                // Extract only the date from the timestamp
                String currentDay = new SimpleDateFormat("EEEE").format(date);

                // Check if it's a new day and append a space if it is
                if (!currentDay.equals(previousDay)) {
                    forecastTextBuilder.append("\n");
                }

                forecastTextBuilder.append("   ").append(currentDay).append("   ").append(description)
                        .append(". Min:").append(tempMin).append("\u00B0C. Max:").append(tempMax)
                        .append("\u00B0C.\n");

                previousDay = currentDay; // Update the previous day
            }

            String forecastText = forecastTextBuilder.toString();
            view.setFourDay(forecastText);


        }
        catch (Exception e){
            JOptionPane.showMessageDialog(view, "Error fetching weather data.");
        }
        view.addSubmitListener(e -> {
            try {

                model.setCity(view.getCity());
                model.setLanguage(view.getLanguage());
                model.fetchCurrentWeatherData("dee99d2e1ba9baa09ddbe17c094d8e49");
                String city= model.getCity();
                String country=model.getCountryName();
                int temp=model.getTemperatureCelsius();
                view.setCityName(city+","+country);
                String s= Integer.toString(temp);
                String format= "<html>" + s + "<sup><font size='+2'>°c</font></sup></html>";
                view.setTempText(format);
                view.setAdditionalLabel(model.getDescription());
                view.updateWeatherIcon(model.getDescription());
                int hum= model.getHumidity();
                s= Integer.toString(hum);
                view.setLabel1(s);
                int wind=model.getWindSpeed();
                s= Integer.toString(wind);
                view.setLabel2(s);
                int cloud=model.getCloudiness();
                s= Integer.toString(cloud);
                view.setLabel3(s);
                s = model.getTempMax() + "°c";
                s = s+"\n"+ model.getTempMin()+"°c";
                view.setLabel4(s);
                view.setLabel5(model.getSunriseTime());
                view.setLabel6(model.getSunsetTime());

                JSONArray forecastData = model.fetchForecastData("dee99d2e1ba9baa09ddbe17c094d8e49");
                StringBuilder forecastTextBuilder = new StringBuilder();
                String previousDay = ""; // Store the previous day for comparison
                forecastTextBuilder.append("\t\t4 DAY WEATHER FORECAST\n\n");
                for (int i = 0; i < forecastData.length(); i++) {
                    JSONObject forecastItem = forecastData.getJSONObject(i);
                    long timestamp = forecastItem.getLong("dt") * 1000;
                    Date date = new Date(timestamp);
                    double tempMin = forecastItem.getJSONObject("main").getDouble("temp_min");
                    double tempMax = forecastItem.getJSONObject("main").getDouble("temp_max");
                    String description = forecastItem.getJSONArray("weather").getJSONObject(0).getString("description");

                    // Extract only the date from the timestamp
                    String currentDay = new SimpleDateFormat("EEEE").format(date);

                    // Check if it's a new day and append a space if it is
                    if (!currentDay.equals(previousDay)) {
                        forecastTextBuilder.append("\n");
                    }

                    forecastTextBuilder.append("   ").append(currentDay).append("   ").append(description)
                            .append(". Min:").append(tempMin).append("\u00B0C. Max:").append(tempMax)
                            .append("\u00B0C.\n");

                    previousDay = currentDay; // Update the previous day
                }

                String forecastText = forecastTextBuilder.toString();
                view.setFourDay(forecastText);
                view.dialog.dispose();
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(view, "Error fetching weather data.");
            }
        });
        view.addAddToFavoritesListener(e -> {
            String city = model.getCity();

            if (!city.isEmpty()) {
                // Add the city to the model's favorite cities list.
                model.addCityToFavorites(city);

                // Update the view's favorite cities list model.
                //favoriteCitiesListModel[0] = view.getFavoriteCitiesListModel();
                if (!favoriteCitiesListModel[0].contains(city)) {
                    favoriteCitiesListModel[0].addElement(city);
                }

                // Save the updated favorite cities list to file.
                model.saveFavoriteCities();
            }
        });
        view.addSearchListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the dialog when the button is clicked
                view.opeDialog();
            }
        });
    }
}
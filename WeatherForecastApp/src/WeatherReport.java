import org.json.JSONObject;

public class WeatherReport extends WeatherService {

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void processWeatherData(String city) {
        try {
            // Fetch the weather data
            String weatherData = fetchWeatherData(city);

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(weatherData);  // Parsing the weather data as JSON
            JSONObject mainData = jsonResponse.getJSONObject("main");
            double temperatureInKelvin = mainData.getDouble("temp");
            double temperatureInFahrenheit = (temperatureInKelvin - 273.15) * 9/5 + 32;
            int humidity = mainData.getInt("humidity");
            String weatherDescription = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");

            // Display the weather data
            System.out.printf("City: %s%n", city);
            System.out.printf("Temperature: %.2f°F%n", temperatureInFahrenheit);
            System.out.println("Humidity: " + humidity + "%");
            System.out.println("Weather: " + weatherDescription);

        } catch (Exception e) {
            System.out.println("Error processing weather data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
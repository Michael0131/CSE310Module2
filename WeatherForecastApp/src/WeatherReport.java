import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherReport extends WeatherService {

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void processWeatherData(String location) {
        try {
            // Fetch the raw weather data as a string
            String weatherData = fetchWeatherData(location);

            // Determine if the location is a city or state
            String locationType = determineLocationType(location);

            // Manually parse the temperature
            String temperaturePart = extractValue(weatherData, "\"temp\":");
            double temperatureInKelvin = Double.parseDouble(temperaturePart);
            double temperatureInFahrenheit = (temperatureInKelvin - 273.15) * 9 / 5 + 32;

            // Manually parse the humidity
            String humidityPart = extractValue(weatherData, "\"humidity\":");
            int humidity = Integer.parseInt(humidityPart);

            // Manually parse the sunset time
            String sunsetPart = extractValue(weatherData, "\"sunset\":");
            long sunsetUnix = Long.parseLong(sunsetPart);
            String sunsetTime = convertUnixToTime(sunsetUnix);

            // Manually parse the weather description
            String weatherDescription = extractWeatherDescription(weatherData);

            // Display the parsed weather data
            System.out.printf("Location (%s): %s%n", locationType, location);
            System.out.printf("Temperature: %.2f°F (%s)%n", temperatureInFahrenheit, getTemperatureContext(temperatureInFahrenheit)); // Fixed
            System.out.printf("Humidity: %d%%%n", humidity);
            System.out.printf("Weather: %s%n", weatherDescription);
            System.out.printf("Sunset Time: %s%n", sunsetTime);

        } catch (Exception e) {
            System.out.println("Error processing weather data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // New method to provide context based on temperature
    private String getTemperatureContext(double temperature) {
        if (temperature < 32) {
            return "Cold";
        } else if (temperature >= 32 && temperature < 50) {
            return "Cool";
        } else if (temperature >= 50 && temperature < 70) {
            return "Mild";
        } else {
            return "Warm";
        }
    }

    // Method to determine whether the location is a city or state
    private String determineLocationType(String location) {
        if (LocationType.STATES.contains(location)) {
            return "State";
        } else {
            return "City"; // Default to City if it's not a known state
        }
    }

    // Utility function to extract values based on a key (like "temp", "humidity")
    private String extractValue(String weatherData, String key) {
        Pattern pattern = Pattern.compile(key + "([0-9.]+)");
        Matcher matcher = pattern.matcher(weatherData);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "0"; // Default value if not found
    }

    // Convert Unix time to a human-readable format (hh:mm a)
    private String convertUnixToTime(long unixSeconds) {
        java.util.Date date = new java.util.Date(unixSeconds * 1000L);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm a"); // For 12-hour format
        sdf.setTimeZone(java.util.TimeZone.getDefault()); // Use default timezone
        return sdf.format(date);
    }

    // New method to extract the weather description
    private String extractWeatherDescription(String weatherData) {
        Pattern pattern = Pattern.compile("\"weather\":\\s*\\[\\{[^}]*\"description\":\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(weatherData);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "Unknown"; // Default if not found
    }
}

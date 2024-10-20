// File: WeatherService.java
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public abstract class WeatherService {
    
    protected String apiKey = "b61071283a63ba5db7cbfd93638badcb";  // Your OpenWeatherMap API key
    
    // Abstract method that subclasses must implement
    public abstract void processWeatherData(String city) throws Exception;

    // Common method for making the API request
    protected String fetchWeatherData(String city) throws Exception {
        String encodedLocation = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + encodedLocation + "&appid=" + apiKey;

        // Create a URL object to connect to the API
        @SuppressWarnings("deprecation")
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            StringBuilder inline = new StringBuilder();
            try (Scanner scanner = new Scanner(url.openStream())) {
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
            }
            return inline.toString();
        } else {
            throw new RuntimeException("Error: Failed to fetch data, Response Code: " + responseCode);
        }
    }
}

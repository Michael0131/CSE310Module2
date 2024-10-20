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
        String encodedLocation = URLEncoder.encode(city, StandardCharsets.UTF_8); // encodes city name for API
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + encodedLocation + "&appid=" + apiKey; // constructs API URL using city name and key

        // Create a URL object to connect to the API
        @SuppressWarnings("deprecation")
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // makes HTTP connection to API
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode(); // Gets response code //
        if (responseCode == 200) {
            StringBuilder inline = new StringBuilder(); //This creates a StringBuilder to construct the response data string by reading from the input stream of the URL. //
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

import java.util.Scanner;

public class Main {
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Ask the user to enter a location
            System.out.println(); // This will add a blank line
            System.out.println(); // This will add a blank line
            System.out.print("Enter a Location - *Can be City or State* - Example: 'New York': ");
            String location = scanner.nextLine();

            // Create an instance of WeatherReport and process the weather data
            WeatherReport report = new WeatherReport();
            report.processWeatherData(location);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
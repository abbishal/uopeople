**Application Overview**
The Weather Information App is a Java-based desktop application developed to provide users with real-time weather updates and short-term forecasts. The application utilizes the OpenWeatherMap API to fetch data and presents it through a user-friendly Graphical User Interface (GUI) built with Java Swing.

**Features Implemented**

1. **API Integration:** Connects to OpenWeatherMap to retrieve current weather and forecast data using HTTP requests.
2. **GUI Design:** A clean interface created using Java Swing components (JFrame, JPanel, GridBagLayout).
3. **Data Display:** Shows temperature, humidity, wind speed, and general conditions.
4. **Visual Representation:** Fetches and renders weather icons directly from the API URL.
5. **Forecast:** displays a text-based 9-hour forecast (3-hour intervals).
6. **Unit Conversion:** Allows toggling between Celsius (Metric) and Fahrenheit (Imperial).
7. **Error Handling:** Catches JSON parsing errors and network timeouts, displaying user-friendly alerts via `JOptionPane`.
8. **Search History:** Tracks the session's search history with timestamps.
9. **Dynamic Background:** The background gradient changes automatically based on the local time of day (Morning/Day, Sunset, Night).

**Implementation Details**

* **Networking:** Utilizes `java.net.HttpURLConnection` for GET requests.
* **JSON Parsing:** Uses the `org.json` external library to parse the API response.
* **Dynamic Graphics:** Overrides the `paintComponent` method in a custom JPanel to draw a `GradientPaint` based on `LocalTime.now()`.

**How to Compile the Application**

1. **Dependencies:** Ensure the `org.json` JAR file is added to the project classpath.
2. **Compile:** Compile the `WeatherApp.java` and get get jar file. (I have done this using VSCode's java plugin but other tools might be handy).

**How to Run the Application**
1. **Execution:** After compiling we will get the jar file. which can be run with: `java -jar Unit8.jar` (change the jar file name if your jar file has a different name)
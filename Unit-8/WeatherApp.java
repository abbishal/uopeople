import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherApp extends JFrame {

    // CONFIGURATION
    private static final String API_KEY = "46280e9a0dab85b46ef10c0df6c8463c"; // REPLACE THIS!
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    // GUI Components
    private JTextField cityInput;
    private JLabel temperatureLabel, conditionLabel, humidityLabel, windLabel, iconLabel;
    private JTextArea historyArea, forecastArea;
    private JPanel mainPanel;
    private boolean isCelsius = true;
    private double currentTempKelvin = 0; // Store raw data
    private double currentWindSpeed = 0;

    // Data Storage
    private List<String> searchHistory = new ArrayList<>();

    public WeatherApp() {
        setTitle("Weather App");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Layout
        mainPanel = new BackgroundPanel(); // Custom panel for dynamic background
        mainPanel.setLayout(new BorderLayout(10, 10));
        add(mainPanel);

        // 1. Top Section: Input
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        cityInput = new JTextField(20);
        JButton searchBtn = new JButton("Get Weather");
        JButton historyBtn = new JButton("View History");
        JButton unitBtn = new JButton("Toggle °C/°F");

        topPanel.add(new JLabel("City: "));
        topPanel.add(cityInput);
        topPanel.add(searchBtn);
        topPanel.add(unitBtn);
        topPanel.add(historyBtn);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 2. Center Section: Current Weather Display
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);

        iconLabel = new JLabel();
        temperatureLabel = new JLabel("Temp: --");
        temperatureLabel.setFont(new Font("Arial", Font.BOLD, 30));
        conditionLabel = new JLabel("Condition: --");
        humidityLabel = new JLabel("Humidity: --");
        windLabel = new JLabel("Wind: --");

        centerPanel.add(iconLabel, gbc);
        centerPanel.add(temperatureLabel, gbc);
        centerPanel.add(conditionLabel, gbc);
        centerPanel.add(humidityLabel, gbc);
        centerPanel.add(windLabel, gbc);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 3. Bottom Section: Forecast
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Short-Term Forecast"));
        bottomPanel.setOpaque(false);
        forecastArea = new JTextArea(5, 40);
        forecastArea.setEditable(false);
        forecastArea.setOpaque(false);
        bottomPanel.add(new JScrollPane(forecastArea), BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // 4. Action Listeners
        searchBtn.addActionListener(e -> fetchWeatherData());
        
        unitBtn.addActionListener(e -> {
            isCelsius = !isCelsius;
            updateDisplayUnits();
        });

        historyBtn.addActionListener(e -> showHistory());
    }

    // --- LOGIC METHODS ---

    private void fetchWeatherData() {
        String city = cityInput.getText().trim();
        if (city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a city name.");
            return;
        }

        try {
            // 1. Fetch Current Weather
            String weatherUrl = BASE_URL + "weather?q=" + city + "&appid=" + API_KEY;
            JSONObject weatherData = getJSON(weatherUrl);

            // 2. Fetch Forecast
            String forecastUrl = BASE_URL + "forecast?q=" + city + "&appid=" + API_KEY;
            JSONObject forecastData = getJSON(forecastUrl);

            // 3. Parse and Update UI
            updateCurrentWeatherUI(weatherData);
            updateForecastUI(forecastData);
            updateBackground();
            addToHistory(city);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Could not find city or connect to API.\nCheck console for details.");
        }
    }

    private JSONObject getJSON(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("HTTP Response Code: " + conn.getResponseCode());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();

        return new JSONObject(content.toString());
    }

    private void updateCurrentWeatherUI(JSONObject json) throws Exception {
        // Temperature (Kelvin)
        currentTempKelvin = json.getJSONObject("main").getDouble("temp");
        
        // Conditions
        String condition = json.getJSONArray("weather").getJSONObject(0).getString("main");
        String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
        String iconCode = json.getJSONArray("weather").getJSONObject(0).getString("icon");
        
        // Humidity & Wind
        int humidity = json.getJSONObject("main").getInt("humidity");
        currentWindSpeed = json.getJSONObject("wind").getDouble("speed"); // Meters/sec

        // Update Labels
        updateDisplayUnits(); // Calculates C/F and updates text
        conditionLabel.setText(condition + " (" + description + ")");
        humidityLabel.setText("Humidity: " + humidity + "%");
        
        // Load Icon
        String iconUrl = "http://openweathermap.org/img/wn/" + iconCode + "@2x.png";
        URL url = new URL(iconUrl);
        BufferedImage image = ImageIO.read(url);
        iconLabel.setIcon(new ImageIcon(image));
    }

    private void updateForecastUI(JSONObject json) {
        StringBuilder sb = new StringBuilder();
        JSONArray list = json.getJSONArray("list");
        
        // Get next 3 entries (usually 3-hour intervals)
        for (int i = 0; i < Math.min(3, list.length()); i++) {
            JSONObject item = list.getJSONObject(i);
            String dtTxt = item.getString("dt_txt"); // Timestamp
            double tempK = item.getJSONObject("main").getDouble("temp");
            String cond = item.getJSONArray("weather").getJSONObject(0).getString("main");
            
            double tempC = tempK - 273.15;
            sb.append(String.format("%s | %.1f°C | %s\n", dtTxt, tempC, cond));
        }
        forecastArea.setText(sb.toString());
    }

    private void updateDisplayUnits() {
        if (isCelsius) {
            double c = currentTempKelvin - 273.15;
            temperatureLabel.setText(String.format("%.1f °C", c));
            windLabel.setText(String.format("Wind: %.1f m/s", currentWindSpeed));
        } else {
            double f = (currentTempKelvin - 273.15) * 9/5 + 32;
            double windMph = currentWindSpeed * 2.237;
            temperatureLabel.setText(String.format("%.1f °F", f));
            windLabel.setText(String.format("Wind: %.1f mph", windMph));
        }
    }

    // Dynamic Background Logic
    private void updateBackground() {
        LocalTime now = LocalTime.now();
        // Trigger repaint to update color in BackgroundPanel
        mainPanel.repaint();
    }

    private void addToHistory(String city) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        searchHistory.add(timeStamp + " - " + city);
    }

    private void showHistory() {
        StringBuilder hist = new StringBuilder();
        for (String s : searchHistory) {
            hist.append(s).append("\n");
        }
        JOptionPane.showMessageDialog(this, new JTextArea(hist.toString()), "Search History", JOptionPane.INFORMATION_MESSAGE);
    }

    // Custom Panel for Gradient Background
    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            LocalTime now = LocalTime.now();
            int hour = now.getHour();

            Color color1, color2;

            if (hour >= 6 && hour < 17) {
                // Day (Blue to Cyan)
                color1 = new Color(135, 206, 235);
                color2 = new Color(224, 255, 255);
            } else if (hour >= 17 && hour < 20) {
                // Sunset (Orange to Purple)
                color1 = new Color(255, 69, 0); 
                color2 = new Color(75, 0, 130);
            } else {
                // Night (Dark Blue to Black)
                color1 = new Color(25, 25, 112);
                color2 = Color.BLACK;
            }

            GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WeatherApp().setVisible(true);
        });
    }
}
package com.weatherAPI.backend.service;

import com.weatherAPI.backend.dto.ForecastDay;
import com.weatherAPI.backend.dto.weatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class weatherService {
    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${weather.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public weatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public weatherResponse getWeather(String city){
        String url = apiUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric";

        Map<String, Object> response = restTemplate.getForObject(url,Map.class);

        Map<String, Object> main = (Map<String, Object>) response.get("main");
        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");
        Map<String, Object> wind = (Map<String, Object>) response.get("wind");

        return weatherResponse.builder()
                .temperature(((Number)main.get("temp")).doubleValue())
                .humidity((((Number)main.get("humidity")).doubleValue()))
                .condition(((String)weatherList.get(0).get("condition")))
                .windSpeed(((Number) wind.get("speed")).doubleValue())
                .build();

    }

        public List<ForecastDay> getForecast(String city) {
            // change /weather to /forecast
            String forecastUrl = apiUrl.replace("/weather", "/forecast")
                    + "?q=" + city + "&appid=" + apiKey + "&units=metric";

            Map<String, Object> response = restTemplate.getForObject(forecastUrl, Map.class);
            List<Map<String, Object>> forecastList = (List<Map<String, Object>>) response.get("list");

            // Group readings by date
            Map<String, List<Map<String, Object>>> groupedByDate = new LinkedHashMap<>();

            for (Map<String, Object> entry : forecastList) {
                String dateTime = (String) entry.get("dt_txt");
                String date = dateTime.split(" ")[0]; // "2026-05-21 12:00:00" → "2026-05-21"

                groupedByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(entry);
            }

            // Build one ForecastDay per date
            List<ForecastDay> result = new ArrayList<>();

            for (Map.Entry<String, List<Map<String, Object>>> entry : groupedByDate.entrySet()) {
                String date = entry.getKey();
                List<Map<String, Object>> readings = entry.getValue();

                double maxTemp = Double.MIN_VALUE;
                double minTemp = Double.MAX_VALUE;
                String description = "";
                String iconUrl = "";

                for (Map<String, Object> reading : readings) {
                    Map<String, Object> main = (Map<String, Object>) reading.get("main");
                    double temp = ((Number) main.get("temp")).doubleValue();

                    if (temp > maxTemp) maxTemp = temp;
                    if (temp < minTemp) minTemp = temp;

                    // take description & icon from midday reading (12:00:00)
                    String dt = (String) reading.get("dt_txt");
                    if (dt.contains("12:00:00")) {
                        List<Map<String, Object>> weatherList =
                                (List<Map<String, Object>>) reading.get("weather");
                        description = (String) weatherList.get(0).get("description");
                        String iconCode = (String) weatherList.get(0).get("icon");
                        iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
                    }
                }

                result.add(ForecastDay.builder()
                        .date(date)
                        .maxTemp(maxTemp)
                        .minTemp(minTemp)
                        .description(description)
                        .iconUrl(iconUrl)
                        .build());
            }

            return result;
        }
    }



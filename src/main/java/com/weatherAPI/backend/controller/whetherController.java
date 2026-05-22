package com.weatherAPI.backend.controller;

import com.weatherAPI.backend.dto.ForecastDay;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.weatherAPI.backend.service.weatherService;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class whetherController {
    private final weatherService weatherService;

    public whetherController(weatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public <WeatherResponse> ResponseEntity<WeatherResponse> getWeather(@RequestParam String city) {
        WeatherResponse weather = (WeatherResponse) weatherService.getWeather(city);
        return ResponseEntity.ok(weather);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpError(HttpClientErrorException ex) {
        if (ex.getStatusCode().value() == 401) {
            return ResponseEntity.status(401).body("Invalid or inactive API key. Check application.properties.");
        }
        if (ex.getStatusCode().value() == 404) {
            return ResponseEntity.status(404).body("City not found.");
        }
        return ResponseEntity.status(ex.getStatusCode()).body("External API error: " + ex.getMessage());
    }

    @GetMapping("/forecast")
    public ResponseEntity<List<ForecastDay>> getForecast(@RequestParam String city) {
        return ResponseEntity.ok(weatherService.getForecast(city));
    }
}

package com.weatherAPI.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class weatherResponse {
    private double temperature;
    private double humidity;
    private String condition;
    private double windSpeed;
}

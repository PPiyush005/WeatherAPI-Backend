package com.weatherAPI.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForecastDay {
    private String date;
    private double maxTemp;
    private double minTemp;
    private String description;
    private String iconUrl;
}

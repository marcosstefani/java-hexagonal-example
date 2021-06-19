package io.github.marcosstefani.hexagonal.core.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ForecastDTO {
    private String city;
    private LocalDateTime referenceDate;
    private BigDecimal currentTemperature;
    private BigDecimal minimumTemperature;
    private BigDecimal maximumTemperature;
    private BigDecimal feelsLike;
    private BigDecimal humidity;
}

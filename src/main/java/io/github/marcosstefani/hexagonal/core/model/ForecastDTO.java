package io.github.marcosstefani.hexagonal.core.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Embeddable
public class ForecastDTO {
    private String city;
    private LocalDateTime referenceDate;
    private BigDecimal currentTemperature;
    private BigDecimal minimumTemperature;
    private BigDecimal maximumTemperature;
    private BigDecimal feelsLike;
    private BigDecimal humidity;
}

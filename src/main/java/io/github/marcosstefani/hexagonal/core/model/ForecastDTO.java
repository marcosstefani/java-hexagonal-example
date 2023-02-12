package io.github.marcosstefani.hexagonal.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public int referenceDateDay() {
        return referenceDate.getDayOfMonth();
    }

    public int referenceDateMonth() {
        return referenceDate.getMonth().getValue();
    }

    public int referenceDateYear() {
        return referenceDate.getYear();
    }
}

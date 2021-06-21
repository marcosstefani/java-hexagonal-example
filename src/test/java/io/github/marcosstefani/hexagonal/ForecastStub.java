package io.github.marcosstefani.hexagonal;

import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ForecastStub {
    public static ForecastDTO buildForecastDTO() {
        return ForecastDTO.builder()
                .city("Curitiba")
                .currentTemperature(BigDecimal.TEN)
                .maximumTemperature(BigDecimal.TEN)
                .minimumTemperature(BigDecimal.ONE)
                .feelsLike(BigDecimal.ZERO)
                .humidity(BigDecimal.TEN)
                .referenceDate(LocalDateTime.now())
                .build();
    }
}

package io.github.marcosstefani.hexagonal.core.port.in;

import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;

import java.time.LocalDate;

public interface ForecastInput {
    ForecastDTO getForecastDataForACity(String city, LocalDate date);
    void updateForecastDataForACity(String city);
}

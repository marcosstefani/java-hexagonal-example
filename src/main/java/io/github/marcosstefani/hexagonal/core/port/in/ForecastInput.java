package io.github.marcosstefani.hexagonal.core.port.in;

import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;

public interface ForecastInput {
    ForecastDTO getCurrentForecastDataForACity(String city);
    void updateForecastDataForACity(String city);
}

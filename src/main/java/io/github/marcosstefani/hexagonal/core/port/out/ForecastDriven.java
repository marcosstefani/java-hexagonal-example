package io.github.marcosstefani.hexagonal.core.port.out;

import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;

import java.util.List;

public interface ForecastDriven {
    List<ForecastDTO> getForecastDataForACity(String city);
    void updateForecastDataForACity(String city);
}

package io.github.marcosstefani.hexagonal.core.port.out;

import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;

import java.util.List;

public interface DatabaseUseCase {
    void saveForecastList(List<ForecastDTO> forecastDTOList);
    ForecastDTO getCurrentForecast(String city);
}

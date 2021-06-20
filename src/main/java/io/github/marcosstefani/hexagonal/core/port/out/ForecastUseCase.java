package io.github.marcosstefani.hexagonal.core.port.out;

import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;

import java.util.List;

public interface ForecastUseCase {
    List<ForecastDTO> getForecastDataForACity(String city);
}

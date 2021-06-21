package io.github.marcosstefani.hexagonal.adapter.postgres;

import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;
import io.github.marcosstefani.hexagonal.core.port.out.DatabaseUseCase;

import java.util.List;

public class PostgresAdapter implements DatabaseUseCase {
    @Override
    public void saveForecastList(List<ForecastDTO> forecastDTOList) {

    }

    @Override
    public ForecastDTO getCurrentForecast(String city) {
        return null;
    }
}

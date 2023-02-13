package io.github.marcosstefani.hexagonal.adapter.postgres;

import io.github.marcosstefani.hexagonal.adapter.postgres.domain.Forecast;
import io.github.marcosstefani.hexagonal.adapter.postgres.repository.ForecastRepository;
import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;
import io.github.marcosstefani.hexagonal.core.port.out.DatabaseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class PostgresAdapter implements DatabaseUseCase {
    private final ForecastRepository forecastRepository;

    @Override
    public void saveForecastList(List<ForecastDTO> forecastDTOList) {
        List<Forecast> forecasts = forecastDTOList.stream().map(forecastDTO ->
                Forecast.builder().forecastDTO(forecastDTO).build()
        ).collect(Collectors.toList());

        List<Forecast> list = forecasts.stream().filter(forecast -> forecastRepository
                .quantityOfForecastByCityAndTime(forecast.getForecastDTO().getCity(), forecast.getForecastDTO().getReferenceDate()) == 0)
                .collect(Collectors.toList());

        forecastRepository.saveAll(list);
    }

    @Override
    public ForecastDTO getCurrentForecast(String city) {
        Forecast currentForecast = forecastRepository.findCurrentForecast(city);
        if (isNull(currentForecast) || isNull(currentForecast.getForecastDTO())) return null;
        return currentForecast.getForecastDTO();
    }

    @Override
    public ForecastDTO getLastForecastByDate(String city, LocalDate date) {
        Forecast currentForecast = forecastRepository.findLastForecastByReferenceDate(city, date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        if (isNull(currentForecast) || isNull(currentForecast.getForecastDTO())) return null;
        return currentForecast.getForecastDTO();
    }

    @Override
    public List<String> getRegisteredCities() {
        List<String> list = forecastRepository.registeredCities();
        if (isNull(list)) {
            return emptyList();
        }
        return list;
    }
}

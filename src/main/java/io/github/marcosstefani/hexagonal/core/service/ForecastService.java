package io.github.marcosstefani.hexagonal.core.service;

import io.github.marcosstefani.hexagonal.core.exception.ForecastNotFoundException;
import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;
import io.github.marcosstefani.hexagonal.core.port.in.ForecastInput;
import io.github.marcosstefani.hexagonal.core.port.out.DatabaseUseCase;
import io.github.marcosstefani.hexagonal.core.port.out.ForecastUseCase;
import io.github.marcosstefani.hexagonal.core.port.out.MessageUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class ForecastService implements ForecastInput {
    private static final Logger logger = LoggerFactory.getLogger(ForecastService.class);

    private final ForecastUseCase forecastUseCase;
    private final DatabaseUseCase databaseUseCase;

    private final MessageUseCase messageUseCase;

    @Override
    public ForecastDTO getForecastDataForACity(String city, LocalDate date) {
        logger.info("Getting {} city forecast information", city);
        ForecastDTO currentForecast = isNull(date) ?
                databaseUseCase.getCurrentForecast(city) :
                databaseUseCase.getLastForecastByDate(city, date);

        if (currentForecast == null) {
            logger.error("Current forecast for {} city not found", city);
            throw new ForecastNotFoundException(String.format("Current forecast for %s city not found", city));
        }
        return currentForecast;
    }

    @Override
    public void updateForecastDataForACity(String city) {
        logger.info("Updating {} city forecast information", city);
        List<ForecastDTO> forecastDTOList = forecastUseCase.getForecastDataForACity(city);
        if (isNull(forecastDTOList) || forecastDTOList.isEmpty()) {
            logger.error("Forecast for {} city not found", city);
            throw new ForecastNotFoundException(String.format("Forecast for %s city not found", city));
        }

        databaseUseCase.saveForecastList(forecastDTOList);
        logger.info("{} city forecast successfully updated.", city);
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void updateForecastForRegisteredCitiesTask() {
        List<String> cities = databaseUseCase.getRegisteredCities();
        cities.forEach(city -> {
            logger.info("{} Publishing update task.", city);
            messageUseCase.updateForecastForOneCity(city);
        });
    }
}

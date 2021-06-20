package io.github.marcosstefani.hexagonal.core.service;

import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;
import io.github.marcosstefani.hexagonal.core.port.in.ForecastInput;
import io.github.marcosstefani.hexagonal.core.port.out.DatabaseUseCase;
import io.github.marcosstefani.hexagonal.core.port.out.ForecastUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastService implements ForecastInput {
    private static Logger logger = LoggerFactory.getLogger(ForecastService.class);

    private ForecastUseCase forecastUseCase;
    private DatabaseUseCase databaseUseCase;

    @Override
    public ForecastDTO getCurrentForecastDataForACity(String city) {
        return null;
    }

    @Override
    public void updateForecastDataForACity(String city) {
        logger.info("Updating {} city forecast information", city);
        List<ForecastDTO> forecastDTOList = forecastUseCase.getForecastDataForACity(city);
        databaseUseCase.savaForecastList(forecastDTOList);
        logger.info("{} city forecast successfully updated.", city);
    }
}

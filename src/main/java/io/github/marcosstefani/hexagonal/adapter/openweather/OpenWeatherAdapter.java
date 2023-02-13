package io.github.marcosstefani.hexagonal.adapter.openweather;

import io.github.marcosstefani.hexagonal.adapter.openweather.util.DateUtil;
import io.github.marcosstefani.hexagonal.adapter.openweather.util.OpenWeatherProperties;
import io.github.marcosstefani.hexagonal.adapter.openweather.model.ForecastResponseDTO;
import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;
import io.github.marcosstefani.hexagonal.core.port.out.ForecastUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class OpenWeatherAdapter implements ForecastUseCase {
    private final static Logger logger = LoggerFactory.getLogger(OpenWeatherAdapter.class);

    private final RestTemplate restTemplate;
    private final OpenWeatherProperties properties;

    @Override
    public List<ForecastDTO> getForecastDataForACity(String city) {
        ForecastResponseDTO responseDTO;
        try {
            responseDTO = restTemplate.getForObject(buildUrl(city), ForecastResponseDTO.class);
        } catch (HttpClientErrorException e) {
            logger.error("Error when consulting the weather forecast to {}", city);
            return emptyList();
        }

        if (responseDTO == null || responseDTO.getList() == null || responseDTO.getList().isEmpty()) return emptyList();

        return responseDTO.getList().stream().map(forecast ->
                ForecastDTO.builder()
                        .city(city)
                        .referenceDate(DateUtil.getLocalDateTime(forecast.getDt()))
                        .humidity(forecast.getMain().getHumidity())
                        .feelsLike(forecast.getMain().getFeelsLike())
                        .currentTemperature(forecast.getMain().getTemp())
                        .minimumTemperature(forecast.getMain().getTempMin())
                        .maximumTemperature(forecast.getMain().getTempMax())
                        .build()
        ).collect(Collectors.toList());
    }

    private String buildUrl(String city) {
        return String.format(properties.getUrl().concat("?q=%s").concat("&appid=%s").concat("&units=metric"), city, properties.getApiKey());
    }
}

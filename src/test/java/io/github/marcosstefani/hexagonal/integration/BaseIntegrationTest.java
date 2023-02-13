package io.github.marcosstefani.hexagonal.integration;

import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;
import io.github.marcosstefani.hexagonal.core.port.out.DatabaseUseCase;
import io.github.marcosstefani.hexagonal.core.port.out.ForecastUseCase;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "integration-test")
public abstract class BaseIntegrationTest {
    @Autowired
    protected TestRestTemplate testRestTemplate;

    @MockBean
    protected DatabaseUseCase databaseUseCase;

    @MockBean
    protected ForecastUseCase forecastUseCase;

    protected void mockGetCurrentForecastFromDatabase(String city) {
        final ForecastDTO forecastDTO = ForecastDTO.builder()
                .city(city)
                .currentTemperature(BigDecimal.TEN)
                .referenceDate(LocalDateTime.now())
                .feelsLike(BigDecimal.TEN.subtract(BigDecimal.ONE))
                .humidity(BigDecimal.ONE)
                .maximumTemperature(BigDecimal.TEN.add(BigDecimal.ONE))
                .minimumTemperature(BigDecimal.ONE)
                .build();

        when(databaseUseCase.getCurrentForecast(city)).thenReturn(forecastDTO);
    }

    protected void mockGetCurrentForecastFromDatabaseWithNull() {
        when(databaseUseCase.getCurrentForecast(anyString())).thenReturn(null);
    }

    protected void mockGetLastForecastByDateFromDatabase(String city, LocalDate date) {
        final ForecastDTO forecastDTO = ForecastDTO.builder()
                .city(city)
                .currentTemperature(BigDecimal.TEN)
                .referenceDate(LocalDateTime.of(date, LocalTime.now()))
                .feelsLike(BigDecimal.TEN.subtract(BigDecimal.ONE))
                .humidity(BigDecimal.ONE)
                .maximumTemperature(BigDecimal.TEN.add(BigDecimal.ONE))
                .minimumTemperature(BigDecimal.ONE)
                .build();

        when(databaseUseCase.getLastForecastByDate(city, date)).thenReturn(forecastDTO);
    }

    protected void mockGetLastForecastByDateFromDatabaseWithNull() {
        when(databaseUseCase.getLastForecastByDate(anyString(), any(LocalDate.class))).thenReturn(null);
    }

    protected List<ForecastDTO> mockGetForecastDataForACity(String city) {
        final LocalDate now = LocalDate.now();
        final ForecastDTO forecastDTO = ForecastDTO.builder()
                .city(city)
                .currentTemperature(BigDecimal.TEN)
                .referenceDate(LocalDateTime.of(now, LocalTime.now()))
                .feelsLike(BigDecimal.TEN.subtract(BigDecimal.ONE))
                .humidity(BigDecimal.ONE)
                .maximumTemperature(BigDecimal.TEN.add(BigDecimal.ONE))
                .minimumTemperature(BigDecimal.ONE)
                .build();

        List<ForecastDTO> result = singletonList(forecastDTO);
        when(forecastUseCase.getForecastDataForACity(city)).thenReturn(result);

        return result;
    }

    protected void mockEmptyGetForecastDataForACity() {
        when(forecastUseCase.getForecastDataForACity(anyString())).thenReturn(emptyList());
    }
}

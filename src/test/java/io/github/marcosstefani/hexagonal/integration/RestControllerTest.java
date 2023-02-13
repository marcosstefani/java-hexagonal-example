package io.github.marcosstefani.hexagonal.integration;

import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RestControllerTest extends BaseIntegrationTest {
    private final String city = "Lisbon";

    @Test
    void shouldSaveInDatabaseWhenEverythingIsOk() {
        final var result = mockGetForecastDataForACity(city);
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity(String.format("/forecast/%s/update", city), null, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(databaseUseCase, times(1)).saveForecastList(result);
    }

    @Test
    void shouldReturnNotFoundStatusWhenForecastNotExists() {
        mockEmptyGetForecastDataForACity();
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity(String.format("/forecast/%s/update", city), null, Void.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(databaseUseCase, times(0)).saveForecastList(anyList());
    }

    @Test
    void shouldReturnTheForecastIfExistsForCurrentDate() {
        mockGetCurrentForecastFromDatabase(city);
        ResponseEntity<ForecastDTO> responseEntity = testRestTemplate.getForEntity(String.format("/forecast/%s", city), ForecastDTO.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(databaseUseCase, times(1)).getCurrentForecast(city);
        verify(databaseUseCase, times(0)).getLastForecastByDate(anyString(), any(LocalDate.class));
    }

    @Test
    void shouldReturnNotFoundIfExistsForCurrentDate() {
        mockGetCurrentForecastFromDatabaseWithNull();
        ResponseEntity<ForecastDTO> responseEntity = testRestTemplate.getForEntity(String.format("/forecast/%s", city), ForecastDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(databaseUseCase, times(1)).getCurrentForecast(city);
        verify(databaseUseCase, times(0)).getLastForecastByDate(anyString(), any(LocalDate.class));
    }

    @Test
    void shouldReturnTheLastForecastIfExistsForSpecificDate() {
        final LocalDate now = LocalDate.now();
        mockGetLastForecastByDateFromDatabase(city, now);
        ResponseEntity<ForecastDTO> responseEntity = testRestTemplate.getForEntity(String.format("/forecast/%s?date=2023-02-13", city), ForecastDTO.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(databaseUseCase, times(0)).getCurrentForecast(city);
        verify(databaseUseCase, times(1)).getLastForecastByDate(anyString(), any(LocalDate.class));
    }

    @Test
    void shouldReturnNotFoundIfExistsForSpecificDate() {
        mockGetLastForecastByDateFromDatabaseWithNull();
        ResponseEntity<ForecastDTO> responseEntity = testRestTemplate.getForEntity(String.format("/forecast/%s?date=2023-02-13", city), ForecastDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(databaseUseCase, times(0)).getCurrentForecast(city);
        verify(databaseUseCase, times(1)).getLastForecastByDate(anyString(), any(LocalDate.class));
    }

    @Test
    void shouldReturnBadRequestIfDateFilterIsWrong() {
        ResponseEntity<ForecastDTO> responseEntity = testRestTemplate.getForEntity(String.format("/forecast/%s?date=2023-2-13", city), ForecastDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        verify(databaseUseCase, times(0)).getCurrentForecast(city);
        verify(databaseUseCase, times(0)).getLastForecastByDate(anyString(), any(LocalDate.class));
    }
}

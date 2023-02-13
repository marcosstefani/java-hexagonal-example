package io.github.marcosstefani.hexagonal.integration;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RestControllerTest extends BaseIntegrationTest {
    private final String city = "Lisbon";

    @Test
    void shouldSaveInDatabaseWhenEverythingIsOk() {
        final var result = mockGetForecastDataForACity(city);
        testRestTemplate.postForEntity(String.format("/forecast/%s/update", city), null, Void.class);

        verify(databaseUseCase, times(1)).saveForecastList(result);
    }
}

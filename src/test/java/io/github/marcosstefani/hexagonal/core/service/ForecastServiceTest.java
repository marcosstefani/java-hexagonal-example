package io.github.marcosstefani.hexagonal.core.service;

import io.github.marcosstefani.hexagonal.ForecastStub;
import io.github.marcosstefani.hexagonal.core.exception.ForecastNotFoundException;
import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;
import io.github.marcosstefani.hexagonal.core.port.out.DatabaseUseCase;
import io.github.marcosstefani.hexagonal.core.port.out.ForecastUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ForecastServiceTest {
    private static final String CURITIBA = "Curitiba";

    @Mock
    private ForecastUseCase forecastUseCase;

    @Mock
    private DatabaseUseCase databaseUseCase;

    @InjectMocks
    private ForecastService forecastService;

    @Test
    void shouldReturnTheReturnedRecordWhenThisOccurs() {
        doReturn(ForecastStub.buildForecastDTO()).when(databaseUseCase).getCurrentForecast(anyString());

        assertDoesNotThrow(() -> forecastService.getCurrentForecastDataForACity(CURITIBA));

        verify(databaseUseCase, times(1)).getCurrentForecast(CURITIBA);
    }

    @Test
    void shouldRaiseExceptionWhenNotFoundReccord() {
        doReturn(null).when(databaseUseCase).getCurrentForecast(anyString());

        assertThrows(ForecastNotFoundException.class, () -> forecastService.getCurrentForecastDataForACity(CURITIBA));
    }

    @Test
    void shouldUpdateDataWhenFoundInformation() {
        List<ForecastDTO> forecastDTOList = Collections.singletonList(ForecastStub.buildForecastDTO());
        doReturn(forecastDTOList).when(forecastUseCase).getForecastDataForACity(anyString());
        doNothing().when(databaseUseCase).saveForecastList(anyList());

        assertDoesNotThrow(() -> forecastService.updateForecastDataForACity(CURITIBA));

        verify(forecastUseCase, times(1)).getForecastDataForACity(CURITIBA);
        verify(databaseUseCase, times(1)).saveForecastList(forecastDTOList);
    }

    @Test
    void shouldRaiseExceptionWhenNotFoundInformation() {
        doReturn(null).when(forecastUseCase).getForecastDataForACity(anyString());

        assertThrows(ForecastNotFoundException.class, () -> forecastService.updateForecastDataForACity(CURITIBA));

        verify(forecastUseCase, times(1)).getForecastDataForACity(CURITIBA);
        verify(databaseUseCase, times(0)).saveForecastList(anyList());
    }
}
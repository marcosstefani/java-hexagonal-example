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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

        assertDoesNotThrow(() -> forecastService.getForecastDataForACity(CURITIBA, null));

        verify(databaseUseCase, times(1)).getCurrentForecast(CURITIBA);
    }

    @Test
    void shouldReturnTheReturnedRecordFromDateWhenThisOccurs() {
        doReturn(ForecastStub.buildForecastDTO()).when(databaseUseCase).getLastForecastByDate(anyString(), any(LocalDate.class));

        final LocalDate date = LocalDate.now();
        assertDoesNotThrow(() -> forecastService.getForecastDataForACity(CURITIBA, date));

        verify(databaseUseCase, times(1)).getLastForecastByDate(CURITIBA, date);
    }

    @Test
    void shouldRaiseExceptionWhenNotFoundRecord() {
        doReturn(null).when(databaseUseCase).getCurrentForecast(anyString());

        assertThrows(ForecastNotFoundException.class, () -> forecastService.getForecastDataForACity(CURITIBA, null));
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
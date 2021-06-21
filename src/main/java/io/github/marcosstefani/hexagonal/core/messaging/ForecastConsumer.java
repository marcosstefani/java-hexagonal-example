package io.github.marcosstefani.hexagonal.core.messaging;

import io.github.marcosstefani.hexagonal.core.port.in.ForecastInput;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ForecastConsumer {
    private final ForecastInput forecastInput;

    @RabbitListener(queues = {"UPDATE_FORECAST"})
    public void updateForecast(@Payload String city) {
        forecastInput.updateForecastDataForACity(city);
    }
}

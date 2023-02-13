package io.github.marcosstefani.hexagonal.adapter.rabbitmq;

import io.github.marcosstefani.hexagonal.core.port.out.MessageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQAdapter implements MessageUseCase {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void updateForecastForOneCity(String city) {
        rabbitTemplate.convertAndSend("UPDATE_FORECAST", city);
    }
}

package io.github.marcosstefani.hexagonal.adapter.rabbitmq.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableRabbit
@EnableScheduling
public class RabbitConfig {
    @Bean
    public Queue queue() {
        return new Queue("UPDATE_FORECAST", true);
    }
}

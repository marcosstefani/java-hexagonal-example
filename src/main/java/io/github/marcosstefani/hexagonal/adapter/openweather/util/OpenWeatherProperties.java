package io.github.marcosstefani.hexagonal.adapter.openweather.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.openweather")
public class OpenWeatherProperties {
    private String apiKey;
    private String url;
    private String timezoneId;
}

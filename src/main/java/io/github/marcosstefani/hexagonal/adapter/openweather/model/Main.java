package io.github.marcosstefani.hexagonal.adapter.openweather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Main {
    private BigDecimal temp;
    @JsonProperty("feels_like")
    private BigDecimal feelsLike;
    @JsonProperty("temp_min")
    private BigDecimal tempMin;
    @JsonProperty("temp_max")
    private BigDecimal tempMax;
    private BigDecimal pressure;
    @JsonProperty("sea_level")
    private BigDecimal seaLevel;
    @JsonProperty("grnd_level")
    private BigDecimal grndLevel;
    private BigDecimal humidity;
    @JsonProperty("temp_kf")
    private BigDecimal tempKf;
}

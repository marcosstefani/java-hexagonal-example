package io.github.marcosstefani.hexagonal.adapter.openweather.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Forecast {
    private Integer dt;
    private Main main;
    private Wind wind;
    private BigDecimal visibility;
}

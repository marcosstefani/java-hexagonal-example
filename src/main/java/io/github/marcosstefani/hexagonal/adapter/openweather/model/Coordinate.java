package io.github.marcosstefani.hexagonal.adapter.openweather.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Coordinate {
    private BigDecimal lat;
    private BigDecimal lon;
}

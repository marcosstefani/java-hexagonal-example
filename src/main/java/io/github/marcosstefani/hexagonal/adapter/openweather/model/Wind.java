package io.github.marcosstefani.hexagonal.adapter.openweather.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Wind {
    private BigDecimal speed;
    private BigDecimal deg;
    private BigDecimal gust;
}

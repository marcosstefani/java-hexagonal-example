package io.github.marcosstefani.hexagonal.adapter.openweather.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ForecastResponseDTO {
    private String cod;
    private BigDecimal message;
    private Integer cnt;
    private List<Forecast> list;
    private City city;
}

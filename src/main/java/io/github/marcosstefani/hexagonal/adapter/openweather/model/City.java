package io.github.marcosstefani.hexagonal.adapter.openweather.model;

import lombok.Data;

import java.math.BigInteger;

@Data
public class City {
    private Integer id;
    private String name;
    private Coordinate coordinate;
    private String country;
    private BigInteger population;
    private Integer timezone;
    private Integer sunrise;
    private Integer sunset;
}

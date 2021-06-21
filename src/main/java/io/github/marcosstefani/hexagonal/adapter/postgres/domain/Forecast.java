package io.github.marcosstefani.hexagonal.adapter.postgres.domain;

import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes = @Index(columnList = "city,referenceDate"))
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private ForecastDTO forecastDTO;
}

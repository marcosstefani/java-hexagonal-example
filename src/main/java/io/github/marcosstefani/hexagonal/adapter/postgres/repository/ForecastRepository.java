package io.github.marcosstefani.hexagonal.adapter.postgres.repository;

import io.github.marcosstefani.hexagonal.adapter.postgres.domain.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Integer> {
}

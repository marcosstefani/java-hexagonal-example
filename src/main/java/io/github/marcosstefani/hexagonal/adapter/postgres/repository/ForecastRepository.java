package io.github.marcosstefani.hexagonal.adapter.postgres.repository;

import io.github.marcosstefani.hexagonal.adapter.postgres.domain.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Integer> {
    // TODO: fix select
    @Query(value = "select f from Forecast f LIMIT 1", nativeQuery = true)
    Forecast findCurrentForecast(@Param("city") String city);
}

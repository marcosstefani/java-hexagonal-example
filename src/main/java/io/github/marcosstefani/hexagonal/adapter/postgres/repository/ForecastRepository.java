package io.github.marcosstefani.hexagonal.adapter.postgres.repository;

import io.github.marcosstefani.hexagonal.adapter.postgres.domain.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Integer> {
    @Query(value = "select f \n" +
            "  from Forecast f \n" +
            " where f.forecastDTO.city  = :city \n" +
            "   and f.forecastDTO.referenceDate = ( \n" +
            "       select max(f2.forecastDTO.referenceDate) \n" +
            "         from Forecast f2 \n" +
            "        where f2.forecastDTO.city = f.forecastDTO.city \n" +
            "          and f2.forecastDTO.referenceDate <= CURRENT_TIMESTAMP() \n" +
            "   )")
    Forecast findCurrentForecast(@Param("city") String city);

    @Query(value = "select id, city, reference_date, current_temperature, minimum_temperature, maximum_temperature, feels_like, humidity\n" +
            "  from forecast f \n" +
            " where f.city = :city \n" +
            "   and extract(day from f.reference_date) = :day \n" +
            "   and extract(month from f.reference_date) = :month \n" +
            "   and extract(year  from f.reference_date) = :year \n" +
            " order by f.reference_date desc\n" +
            " limit 1", nativeQuery = true)
    Forecast findLastForecastByReferenceDate(@Param("city") String city, @Param("year") int year, @Param("month") int month, @Param("day") int day);

    @Query(value = "select count(f) from Forecast f where f.forecastDTO.city = :city and f.forecastDTO.referenceDate = :referenceDate")
    Integer quantityOfForecastByCityAndTime(@Param("city") String city, @Param(("referenceDate")) LocalDateTime referenceDate);
}

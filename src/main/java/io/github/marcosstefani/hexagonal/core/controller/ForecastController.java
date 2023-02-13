package io.github.marcosstefani.hexagonal.core.controller;

import io.github.marcosstefani.hexagonal.core.exception.ForecastNotFoundException;
import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;
import io.github.marcosstefani.hexagonal.core.port.in.ForecastInput;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("forecast")
@RequiredArgsConstructor
public class ForecastController {
    private final ForecastInput forecastInput;

    @PostMapping("{city}/update")
    public ResponseEntity<Void> updateForecastDataForACity(@PathVariable String city) {
        try {
            forecastInput.updateForecastDataForACity(city);
            return ResponseEntity.ok().build();
        } catch (ForecastNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{city}")
    public ResponseEntity<ForecastDTO> getForecastDataForACity(@PathVariable String city,
                      @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            ForecastDTO forecast = forecastInput.getForecastDataForACity(city, date);
            return ResponseEntity.ok().body(forecast);
        } catch (ForecastNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

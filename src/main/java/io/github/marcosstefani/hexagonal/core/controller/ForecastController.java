package io.github.marcosstefani.hexagonal.core.controller;

import io.github.marcosstefani.hexagonal.core.model.ForecastDTO;
import io.github.marcosstefani.hexagonal.core.port.in.ForecastInput;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("forecast")
@RequiredArgsConstructor
public class ForecastController {
    private final ForecastInput forecastInput;

    @PostMapping("{city}/update")
    public void updateForecastDataForACity(@PathVariable String city) {
        forecastInput.updateForecastDataForACity(city);
    }

    @GetMapping("{city}")
    public ForecastDTO getCurrentForecastDataForACity(@PathVariable String city) {
        return forecastInput.getCurrentForecastDataForACity(city);
    }
}

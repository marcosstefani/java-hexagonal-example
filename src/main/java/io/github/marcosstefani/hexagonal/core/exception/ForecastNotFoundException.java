package io.github.marcosstefani.hexagonal.core.exception;

public class ForecastNotFoundException extends RuntimeException {
    public ForecastNotFoundException(String message) {
        super(message);
    }
}

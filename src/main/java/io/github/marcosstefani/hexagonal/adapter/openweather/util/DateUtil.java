package io.github.marcosstefani.hexagonal.adapter.openweather.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {
    public static LocalDateTime getLocalDateTime(int instant) {
        return Instant.ofEpochSecond(instant).atZone(ZoneId.of("Europe/Lisbon")).toLocalDateTime();
    }
}

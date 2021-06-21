package io.github.marcosstefani.hexagonal.adapter.openweather.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {
    @Test
    void shouldReturnLocalDateTime() {
        LocalDateTime calc = DateUtil.getLocalDateTime(1624244400);
        assertEquals(LocalDateTime.of(2021, 6, 21, 0, 0, 0), calc);
    }
}
package com.umbrella.currencyconverter.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateHelper {
    public static Date getUtcNow() {
        return Date.from(Instant.now());
    }

    public static String getDateString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}

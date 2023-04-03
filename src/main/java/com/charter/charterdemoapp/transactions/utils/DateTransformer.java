package com.charter.charterdemoapp.transactions.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateTransformer {

    public static OffsetDateTime localDateToOffsetDateTime(LocalDate localDate) {
        return OffsetDateTime.of(LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth()), LocalTime.now(), ZoneOffset.UTC);
    }
}

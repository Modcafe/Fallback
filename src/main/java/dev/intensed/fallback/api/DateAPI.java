package dev.intensed.fallback.api;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class DateAPI {
    private DateAPI() {
    }

    public static LocalDate today() {
        return LocalDate.now();
    }

    public static LocalTime time_milliseconds() {
        return LocalTime.now();
    }

    public static String time() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static int day() {
        return DateAPI.today().getDayOfMonth();
    }

    public static int month() {
        return DateAPI.today().getMonthValue();
    }

    public static int year() {
        return DateAPI.today().getYear();
    }

    public static DayOfWeek dayOfWeek() {
        return DateAPI.today().getDayOfWeek();
    }
}


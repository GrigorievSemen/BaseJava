package ru.mystudies.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate fromInt(String value) {
        if (value.equals("")) {
            return null;
        }
        String str[] = value.split("-");
        return LocalDate.of(Integer.parseInt(str[0]), Integer.parseInt(str[1]), 1);
    }

    public static String format(LocalDate date) {
        if (date == null) return "";
        return date.format(DATE_TIME_FORMATTER).equals(LocalDate.now().format(DATE_TIME_FORMATTER)) ? "Сейчас" : date.format(DATE_TIME_FORMATTER);
    }
}

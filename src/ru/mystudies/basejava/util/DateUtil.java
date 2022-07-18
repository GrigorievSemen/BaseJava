package ru.mystudies.basejava.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {
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
}

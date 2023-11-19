package edu.hw5.task2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.util.LinkedList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class FridaysFinder {
    private static final int DAY_OF_MONTH_13 = 13;
    private static final TemporalAdjuster NEXT_FRIDAY_13_TH = temporal -> {
        LocalDate date = LocalDate.from(temporal);
        int day = date.getDayOfMonth();
        if (day > DAY_OF_MONTH_13) {
            date = date.withDayOfMonth(DAY_OF_MONTH_13).plusMonths(1);
        } else {
            date = date.withDayOfMonth(DAY_OF_MONTH_13);
        }
        while (!date.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
            date = date.plusMonths(1);
        }
        return date;
    };

    private FridaysFinder() {
    }

    public static List<LocalDate> findAllFriday13th(int year) {
        LocalDate date = LocalDate.of(year, 1, DAY_OF_MONTH_13);
        List<LocalDate> fridays13th = new LinkedList<>();
        while (date.getYear() == year) {
            if (date.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                fridays13th.add(date);
            }
            date = date.plusMonths(1);
        }
        return fridays13th;
    }

    public static LocalDate getNextFriday13th(@NotNull LocalDate date) {
        return date.with(NEXT_FRIDAY_13_TH);
    }
}

package edu.hw5.task2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.util.LinkedList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class FridaysFinder {
    private static final TemporalAdjuster NEXT_FRIDAY_13_TH = temporal -> {
        LocalDate date = LocalDate.from(temporal);
        return findAllFriday13th(date.getYear()).stream()
            .filter(dateTime -> dateTime.isAfter(date))
            .findFirst()
            .orElse(findAllFriday13th(date.getYear() + 1).get(0));
    };

    private FridaysFinder() {
    }

    @SuppressWarnings("MagicNumber")
    public static List<LocalDate> findAllFriday13th(int year) {
        LocalDate date = LocalDate.of(year, 1, 13);
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

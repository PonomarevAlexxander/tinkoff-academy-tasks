package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;

public class DateParser {
    private DateParser() {
    }

    public static Optional<LocalDate> parseDate(String dateString) {
        if (dateString == null) {
            return Optional.empty();
        }
        DateFormatProcessor processorsChain = new DashDividedDateProcessor(
            new DayShiftDateProcessor(
                new SlashDividedDateProcessor(
                    new WordDateProcessor(null))));
        return processorsChain.parseDate(dateString);
    }
}

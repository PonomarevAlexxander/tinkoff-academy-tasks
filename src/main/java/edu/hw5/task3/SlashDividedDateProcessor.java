package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlashDividedDateProcessor extends DateFormatProcessor {
    private static final Pattern DATE_PATTERN = Pattern
        .compile("(?<day>\\d{1,2})/(?<month>\\d{1,2})/(?<year>\\d{1,4})");

    public SlashDividedDateProcessor(DateFormatProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public Optional<LocalDate> parseDate(String dateString) {
        Matcher matcher = DATE_PATTERN.matcher(dateString);
        if (matcher.find()) {
            int year = Integer.parseInt(matcher.group("year"));
            int month = Integer.parseInt(matcher.group("month"));
            int day = Integer.parseInt(matcher.group("day"));
            return Optional.of(LocalDate.of(year, month, day));
        }
        if (nextProcessor != null) {
            return nextProcessor.parseDate(dateString);
        }
        return Optional.empty();
    }
}

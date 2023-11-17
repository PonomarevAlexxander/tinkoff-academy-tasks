package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayShiftDateProcessor extends DateFormatProcessor {
    private static final Pattern DATE_PATTERN = Pattern.compile("(\\d+) days? ago");

    public DayShiftDateProcessor(DateFormatProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public Optional<LocalDate> parseDate(String dateString) {
        Matcher matcher = DATE_PATTERN.matcher(dateString);
        if (matcher.find()) {
            int daysShift = Integer.parseInt(matcher.group(1));
            return Optional.of(LocalDate.now().minusDays(daysShift));
        }
        return super.parseDate(dateString);
    }
}

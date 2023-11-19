package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;

public abstract class DateFormatProcessor {
    protected DateFormatProcessor nextProcessor;

    public DateFormatProcessor(DateFormatProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public Optional<LocalDate> parseDate(String dateString) {
        if (nextProcessor != null) {
            return nextProcessor.parseDate(dateString);
        }
        return Optional.empty();
    }
}

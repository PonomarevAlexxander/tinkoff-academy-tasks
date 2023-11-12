package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;

public abstract class DateFormatProcessor {
    protected DateFormatProcessor nextProcessor;

    public DateFormatProcessor(DateFormatProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public abstract Optional<LocalDate> parseDate(String dateString);
}

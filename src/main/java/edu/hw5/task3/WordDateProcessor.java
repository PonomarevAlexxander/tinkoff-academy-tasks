package edu.hw5.task3;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public class WordDateProcessor extends DateFormatProcessor {
    private static final List<DayReference> SUPPORTED_DAYS = List.of(
        new DayReference("tomorrow", 1),
        new DayReference("yesterday", -1),
        new DayReference("today", 0)
    );

    public WordDateProcessor(DateFormatProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public Optional<LocalDate> parseDate(String dateString) {
        String dateToFind = dateString.strip();
        Optional<DayReference> day = SUPPORTED_DAYS.stream()
            .filter(dayReference -> dayReference.day.equals(dateToFind))
            .findFirst();
        if (day.isPresent()) {
            return Optional.of(LocalDate.now().plusDays(day.get().daysShift));
        }
        return super.parseDate(dateString);
    }

    private record DayReference(String day, Integer daysShift) implements Comparable<DayReference> {
        @Override
        public int compareTo(@NotNull DayReference o) {
            return day.compareTo(o.day);
        }
    }
}

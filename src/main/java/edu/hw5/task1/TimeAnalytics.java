package edu.hw5.task1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TimeAnalytics {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");

    private TimeAnalytics() {
    }

    public static Duration averageSessionTime(List<String> sessionsTime) {
        return Duration.ofMillis((long) sessionsTime.stream()
            .map(TimeAnalytics::extractDuration)
            .mapToLong(Duration::toMillis)
            .average()
            .orElseThrow());
    }

    private static Duration extractDuration(String timePeriod) {
        String[] beginEnd = timePeriod.split(" - ");
        if (beginEnd.length != 2) {
            throw new IllegalArgumentException(
                "input string doesn't match pattern: 2022-03-12, 20:20 - 2022-03-12, 23:50");
        }
        LocalDateTime begin = LocalDateTime.from(DATE_FORMAT.parse(beginEnd[0]));
        LocalDateTime end = LocalDateTime.from(DATE_FORMAT.parse(beginEnd[1]));
        return Duration.between(begin, end);
    }
}

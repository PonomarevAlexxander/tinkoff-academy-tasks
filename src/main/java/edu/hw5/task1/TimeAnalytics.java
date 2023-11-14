package edu.hw5.task1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeAnalytics {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
    private static final Pattern DURATION_PATTERN =
        Pattern.compile("(?<begin>\\d{4}(?:-\\d{2}){2}, \\d{2}:\\d{2}) - (?<end>\\d{4}(?:-\\d{2}){2}, \\d{2}:\\d{2})");

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
        Matcher matcher = DURATION_PATTERN.matcher(timePeriod);
        if (!matcher.find()) {
            throw new IllegalArgumentException(
                "input string doesn't match pattern: 2022-03-12, 20:20 - 2022-03-12, 23:50");
        }
        LocalDateTime begin = LocalDateTime.from(DATE_FORMAT.parse(matcher.group("begin")));
        LocalDateTime end = LocalDateTime.from(DATE_FORMAT.parse(matcher.group("end")));
        return Duration.between(begin, end);
    }
}
